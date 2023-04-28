package cn.iocoder.oauth.module.pay.convert.app;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppPageItemRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppPageItemRespVO.PayMerchant;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppUpdateReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:46+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class PayAppConvertImpl implements PayAppConvert {

    @Override
    public PayAppPageItemRespVO pageConvert(PayAppDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayAppPageItemRespVO payAppPageItemRespVO = new PayAppPageItemRespVO();

        payAppPageItemRespVO.setName( bean.getName() );
        payAppPageItemRespVO.setStatus( bean.getStatus() );
        payAppPageItemRespVO.setRemark( bean.getRemark() );
        payAppPageItemRespVO.setPayNotifyUrl( bean.getPayNotifyUrl() );
        payAppPageItemRespVO.setRefundNotifyUrl( bean.getRefundNotifyUrl() );
        payAppPageItemRespVO.setMerchantId( bean.getMerchantId() );
        payAppPageItemRespVO.setId( bean.getId() );
        payAppPageItemRespVO.setCreateTime( bean.getCreateTime() );

        return payAppPageItemRespVO;
    }

    @Override
    public PayMerchant convert(PayMerchantDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayMerchant payMerchant = new PayMerchant();

        payMerchant.setId( bean.getId() );
        payMerchant.setName( bean.getName() );

        return payMerchant;
    }

    @Override
    public PayAppDO convert(PayAppCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        PayAppDO payAppDO = new PayAppDO();

        payAppDO.setName( bean.getName() );
        payAppDO.setStatus( bean.getStatus() );
        payAppDO.setRemark( bean.getRemark() );
        payAppDO.setPayNotifyUrl( bean.getPayNotifyUrl() );
        payAppDO.setRefundNotifyUrl( bean.getRefundNotifyUrl() );
        payAppDO.setMerchantId( bean.getMerchantId() );

        return payAppDO;
    }

    @Override
    public PayAppDO convert(PayAppUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        PayAppDO payAppDO = new PayAppDO();

        payAppDO.setId( bean.getId() );
        payAppDO.setName( bean.getName() );
        payAppDO.setStatus( bean.getStatus() );
        payAppDO.setRemark( bean.getRemark() );
        payAppDO.setPayNotifyUrl( bean.getPayNotifyUrl() );
        payAppDO.setRefundNotifyUrl( bean.getRefundNotifyUrl() );
        payAppDO.setMerchantId( bean.getMerchantId() );

        return payAppDO;
    }

    @Override
    public PayAppRespVO convert(PayAppDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayAppRespVO payAppRespVO = new PayAppRespVO();

        payAppRespVO.setName( bean.getName() );
        payAppRespVO.setStatus( bean.getStatus() );
        payAppRespVO.setRemark( bean.getRemark() );
        payAppRespVO.setPayNotifyUrl( bean.getPayNotifyUrl() );
        payAppRespVO.setRefundNotifyUrl( bean.getRefundNotifyUrl() );
        payAppRespVO.setMerchantId( bean.getMerchantId() );
        payAppRespVO.setId( bean.getId() );
        payAppRespVO.setCreateTime( bean.getCreateTime() );

        return payAppRespVO;
    }

    @Override
    public List<PayAppRespVO> convertList(List<PayAppDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PayAppRespVO> list1 = new ArrayList<PayAppRespVO>( list.size() );
        for ( PayAppDO payAppDO : list ) {
            list1.add( convert( payAppDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<PayAppRespVO> convertPage(PageResult<PayAppDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<PayAppRespVO> pageResult = new PageResult<PayAppRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }
}
