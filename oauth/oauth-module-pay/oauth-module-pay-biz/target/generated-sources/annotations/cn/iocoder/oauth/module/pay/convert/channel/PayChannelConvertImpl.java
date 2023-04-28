package cn.iocoder.oauth.module.pay.convert.channel;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.channel.PayChannelRespVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayChannelDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T14:54:46+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class PayChannelConvertImpl implements PayChannelConvert {

    @Override
    public PayChannelDO convert(PayChannelCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        PayChannelDO payChannelDO = new PayChannelDO();

        payChannelDO.setCode( bean.getCode() );
        payChannelDO.setStatus( bean.getStatus() );
        payChannelDO.setFeeRate( bean.getFeeRate() );
        payChannelDO.setRemark( bean.getRemark() );
        payChannelDO.setMerchantId( bean.getMerchantId() );
        payChannelDO.setAppId( bean.getAppId() );

        return payChannelDO;
    }

    @Override
    public PayChannelRespVO convert(PayChannelDO bean) {
        if ( bean == null ) {
            return null;
        }

        PayChannelRespVO payChannelRespVO = new PayChannelRespVO();

        payChannelRespVO.setCode( bean.getCode() );
        payChannelRespVO.setStatus( bean.getStatus() );
        payChannelRespVO.setRemark( bean.getRemark() );
        payChannelRespVO.setFeeRate( bean.getFeeRate() );
        payChannelRespVO.setMerchantId( bean.getMerchantId() );
        payChannelRespVO.setAppId( bean.getAppId() );
        payChannelRespVO.setId( bean.getId() );
        payChannelRespVO.setCreateTime( bean.getCreateTime() );

        payChannelRespVO.setConfig( cn.iocoder.oauth.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()) );

        return payChannelRespVO;
    }

    @Override
    public List<PayChannelRespVO> convertList(List<PayChannelDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PayChannelRespVO> list1 = new ArrayList<PayChannelRespVO>( list.size() );
        for ( PayChannelDO payChannelDO : list ) {
            list1.add( convert( payChannelDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<PayChannelRespVO> pageResult = new PageResult<PayChannelRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }
}
