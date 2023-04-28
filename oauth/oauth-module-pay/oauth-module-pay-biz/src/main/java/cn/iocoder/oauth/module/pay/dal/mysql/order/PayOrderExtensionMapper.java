package cn.iocoder.oauth.module.pay.dal.mysql.order;

import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayOrderExtensionMapper extends BaseMapperX<PayOrderExtensionDO> {

    default PayOrderExtensionDO selectByNo(String no) {
        return selectOne(PayOrderExtensionDO::getNo, no);
    }



}
