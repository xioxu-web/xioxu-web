package cn.iocoder.oauth.module.pay.dal.mysql.notify;

import cn.iocoder.oauth.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.oauth.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayNotifyLogCoreMapper extends BaseMapperX<PayNotifyLogDO> {
}
