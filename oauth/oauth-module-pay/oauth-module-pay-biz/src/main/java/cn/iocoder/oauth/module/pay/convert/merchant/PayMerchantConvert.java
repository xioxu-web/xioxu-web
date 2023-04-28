package cn.iocoder.oauth.module.pay.convert.merchant;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantUpdateReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PayMerchantConvert {

    PayMerchantConvert INSTANCE = Mappers.getMapper(PayMerchantConvert.class);

    PayMerchantDO convert(PayMerchantCreateReqVO bean);

    PayMerchantDO convert(PayMerchantUpdateReqVO bean);

    PayMerchantRespVO convert(PayMerchantDO bean);

    List<PayMerchantRespVO> convertList(List<PayMerchantDO> list);

    PageResult<PayMerchantRespVO> convertPage(PageResult<PayMerchantDO> page);



}
