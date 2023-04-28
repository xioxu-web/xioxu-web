package cn.iocoder.oauth.module.pay.convert.merchant;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantUpdateReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:46+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class PayMerchantConvertImpl implements PayMerchantConvert {

    @Override
    public PayMerchantDO convert(PayMerchantCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        PayMerchantDO payMerchantDO = new PayMerchantDO();

        payMerchantDO.setName( bean.getName() );
        payMerchantDO.setShortName( bean.getShortName() );
        payMerchantDO.setStatus( bean.getStatus() );
        payMerchantDO.setRemark( bean.getRemark() );

        return payMerchantDO;
    }

    @Override
    public PayMerchantDO convert(PayMerchantUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        PayMerchantDO payMerchantDO = new PayMerchantDO();

        payMerchantDO.setId( bean.getId() );
        payMerchantDO.setName( bean.getName() );
        payMerchantDO.setShortName( bean.getShortName() );
        payMerchantDO.setStatus( bean.getStatus() );
        payMerchantDO.setRemark( bean.getRemark() );

        return payMerchantDO;
    }

    @Override
    public PayMerchantRespVO convert(PayMerchantDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayMerchantRespVO payMerchantRespVO = new PayMerchantRespVO();

        payMerchantRespVO.setName( bean.getName() );
        payMerchantRespVO.setShortName( bean.getShortName() );
        payMerchantRespVO.setStatus( bean.getStatus() );
        payMerchantRespVO.setRemark( bean.getRemark() );
        payMerchantRespVO.setId( bean.getId() );
        payMerchantRespVO.setCreateTime( bean.getCreateTime() );
        payMerchantRespVO.setNo( bean.getNo() );

        return payMerchantRespVO;
    }

    @Override
    public List<PayMerchantRespVO> convertList(List<PayMerchantDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PayMerchantRespVO> list1 = new ArrayList<PayMerchantRespVO>( list.size() );
        for ( PayMerchantDO payMerchantDO : list ) {
            list1.add( convert( payMerchantDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<PayMerchantRespVO> convertPage(PageResult<PayMerchantDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<PayMerchantRespVO> pageResult = new PageResult<PayMerchantRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }
}
