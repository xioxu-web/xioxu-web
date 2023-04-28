package cn.iocoder.oauth.module.system.service.sms;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.template.SmsTemplateCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.template.SmsTemplatePageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsTemplateDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 短信模板业务接口
 * @author xiaoxu123
 */
public interface SmsTemplateService {

    /**
     * 初始化短信模板的本地缓存
     */
    void initLocalCache();

    /**
     * 获得短信模板，从缓存中
     *
     * @param code 模板编码
     * @return 短信模板
     */
    SmsTemplateDO getSmsTemplateByCodeFromCache(String code);

    /**
     * 格式化短信内容
     *
     * @param content 短信模板的内容
     * @param params 内容的参数
     * @return 格式化后的内容
     */
    String formatSmsTemplateContent(String content, Map<String, Object> params);

    /**
     * 获得短信模板
     *
     * @param code 模板编码
     * @return 短信模板
     */
    SmsTemplateDO getSmsTemplateByCode(String code);

    /**
     * 创建短信模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsTemplate(@Valid SmsTemplateCreateReqVO createReqVO);

    /**
     * 获得短信模板
     *
     * @param id 编号
     * @return 短信模板
     */
    SmsTemplateDO getSmsTemplate(Long id);

    /**
     * 获得短信模板列表
     *
     * @param ids 编号
     * @return 短信模板列表
     */
    List<SmsTemplateDO> getSmsTemplateList(Collection<Long> ids);

    /**
     * 获得短信模板分页
     *
     * @param pageReqVO 分页查询
     * @return 短信模板分页
     */
    PageResult<SmsTemplateDO> getSmsTemplatePage(SmsTemplatePageReqVO pageReqVO);


    /**
     * 获得指定短信渠道下的短信模板数量
     *
     * @param channelId 短信渠道编号
     * @return 数量
     */
    Long countByChannelId(Long channelId);
}
