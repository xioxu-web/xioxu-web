package cn.iocoder.oauth.module.system.convert.sms;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.sms.core.property.SmsChannelProperties;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelRespVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 * @author admin
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SmsChannelDO convert(SmsChannelCreateReqVO bean);

    SmsChannelDO convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SmsChannelDO bean);

    List<SmsChannelRespVO> convertList(List<SmsChannelDO> list);

    PageResult<SmsChannelRespVO> convertPage(PageResult<SmsChannelDO> page);

    List<SmsChannelProperties> convertList02(List<SmsChannelDO> list);

    List<SmsChannelSimpleRespVO> convertList03(List<SmsChannelDO> list);

}
