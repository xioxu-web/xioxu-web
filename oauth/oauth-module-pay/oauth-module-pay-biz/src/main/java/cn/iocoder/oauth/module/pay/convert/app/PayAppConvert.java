package cn.iocoder.oauth.module.pay.convert.app;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppPageItemRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppUpdateReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 支付应用信息 Convert
 *
 * @author admin
 */
@Mapper
public interface PayAppConvert {

    PayAppConvert INSTANCE = Mappers.getMapper(PayAppConvert.class);

    PayAppPageItemRespVO pageConvert (PayAppDO bean);

    PayAppPageItemRespVO.PayMerchant convert(PayMerchantDO bean);

    PayAppDO convert(PayAppCreateReqVO bean);

    PayAppDO convert(PayAppUpdateReqVO bean);

    PayAppRespVO convert(PayAppDO bean);

    List<PayAppRespVO> convertList(List<PayAppDO> list);

    PageResult<PayAppRespVO> convertPage(PageResult<PayAppDO> page);


}
