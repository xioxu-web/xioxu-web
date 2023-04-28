package cn.iocoder.oauth.module.pay.convert.channel;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.channel.PayChannelRespVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Mapper
public interface PayChannelConvert {

    PayChannelConvert INSTANCE = Mappers.getMapper(PayChannelConvert.class);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelCreateReqVO bean);

    @Mapping(target = "config",expression = "java(cn.iocoder.oauth.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()))")
    PayChannelRespVO convert(PayChannelDO bean);

    List<PayChannelRespVO> convertList(List<PayChannelDO> list);

    PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page);

}
