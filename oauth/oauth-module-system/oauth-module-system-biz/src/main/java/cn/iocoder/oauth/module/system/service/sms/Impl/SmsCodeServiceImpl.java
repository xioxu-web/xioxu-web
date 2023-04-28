package cn.iocoder.oauth.module.system.service.sms.Impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.iocoder.oauth.framework.common.exception.ServerException;
import cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.oauth.framework.common.util.collection.MapUtils;
import cn.iocoder.oauth.framework.common.util.date.DateUtils;
import cn.iocoder.oauth.module.system.api.sms.dto.code.SmsCodeCheckReqDTO;
import cn.iocoder.oauth.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.iocoder.oauth.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsCodeDO;
import cn.iocoder.oauth.module.system.dal.mysql.sms.SmsCodeMapper;
import cn.iocoder.oauth.module.system.enums.sms.SmsSceneEnum;
import cn.iocoder.oauth.module.system.framework.sms.SmsCodeProperties;
import cn.iocoder.oauth.module.system.service.sms.SmsCodeService;
import cn.iocoder.oauth.module.system.service.sms.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;

import static cn.hutool.core.util.RandomUtil.randomInt;
import static cn.iocoder.oauth.module.system.enums.ErrorCodeConstants.*;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class SmsCodeServiceImpl implements SmsCodeService {

    @Resource
    private SmsCodeMapper smsCodeMapper;

    @Resource
    private SmsCodeProperties smsCodeProperties;

    @Resource
    private SmsSendService smsSendService;

    @Override
    public void sendSmsCode(@Valid SmsCodeSendReqDTO reqDTO) {
        SmsSceneEnum sceneEnum = SmsSceneEnum.getCodeByScene( reqDTO.getScene() );
        Assert.notNull(sceneEnum, "验证码场景({}) 查找不到配置", reqDTO.getScene());
        //创建验证码
        String smsCode = createSmsCode( reqDTO.getMobile(), reqDTO.getScene(), reqDTO.getCreateIp() );
        // 发送验证码
        smsSendService.sendSingleSms(reqDTO.getMobile(),null,null,sceneEnum.getTemplateCode(), MapUtil.of("smsCode",smsCode));
    }

    private String createSmsCode(String phone,Integer scene,String ip){
    //判断是否可以发送验证码
        SmsCodeDO lastSmsCode = smsCodeMapper.selectLastByMobile( phone, null, null );
        if(lastSmsCode!=null){
          //验证码发送过于频繁
          if(System.currentTimeMillis()-lastSmsCode.getCreateTime().getTime()<smsCodeProperties.getSendFrequency().toMillis()){
            throw ServiceExceptionUtil.exception(SMS_CODE_SEND_TOO_FAST);
          }
          //短信发送超过当天的上限
          if(DateUtils.isToday(lastSmsCode.getCreateTime()) && lastSmsCode.getTodayIndex()>=smsCodeProperties.getSendMaximumQuantityPerDay()){
             throw ServiceExceptionUtil.exception(SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY);
          }
        }
          //创建验证码记录
        String code = String.valueOf( randomInt( smsCodeProperties.getBeginCode(), smsCodeProperties.getEndCode() + 1 ) );
        SmsCodeDO newSmsCode = SmsCodeDO.builder().mobile( phone ).code( code ).scene(scene)
                .todayIndex( lastSmsCode != null && DateUtils.isToday( lastSmsCode.getCreateTime() ) ? lastSmsCode.getTodayIndex() + 1 : 1 )
                .createIp(ip).used(false).build();
        smsCodeMapper.insert(newSmsCode);
        return code;
    }

    @Override
    public void useSmsCode(@Valid SmsCodeUseReqDTO reqDTO) {
     //检测验证码是否有效
        SmsCodeDO lastSmsCode = this.checkSmsCode0(reqDTO.getMobile(), reqDTO.getCode(), reqDTO.getScene());
        smsCodeMapper.updateById(SmsCodeDO.builder().id(lastSmsCode.getId())
                .used(true).usedTime(new Date()).usedIp(reqDTO.getUsedIp()).build());
    }

    @Override
    public void checkSmsCode(@Valid SmsCodeCheckReqDTO reqDTO) {
     checkSmsCode0(reqDTO.getMobile(),reqDTO.getCode(),reqDTO.getScene());
    }

    public SmsCodeDO checkSmsCode0(String mobile, String code, Integer scene) {
        // 校验验证码
        SmsCodeDO lastSmsCode = smsCodeMapper.selectLastByMobile(mobile,code,scene);
        // 若验证码不存在，抛出异常
        if (lastSmsCode == null) {
            throw ServiceExceptionUtil.exception( SMS_CODE_NOT_FOUND );
        }
        // 超过时间
        if (System.currentTimeMillis() - lastSmsCode.getCreateTime().getTime()
                >= smsCodeProperties.getExpireTimes().toMillis()) { // 验证码已过期
            throw ServiceExceptionUtil.exception(SMS_CODE_EXPIRED);
        }
        // 判断验证码是否已被使用
        if (Boolean.TRUE.equals(lastSmsCode.getUsed())) {
            throw ServiceExceptionUtil.exception(SMS_CODE_USED);
        }
        return lastSmsCode;
    }

}
