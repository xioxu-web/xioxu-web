package cn.iocoder.oauth.module.system.service.sms;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.sms.SmsChannelDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 短信渠道业务接口
 * @author xiaoxu123
 */
public interface SmsChannelService {
    /**
     * 初始化短信客户端
     */
    void initSmsClients();

    /**
     * 创建短信渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsChannel(@Valid SmsChannelCreateReqVO createReqVO);

    /**
     * 获得短信渠道
     *
     * @param id 编号
     * @return 短信渠道
     */
    SmsChannelDO getSmsChannel(Long id);

    /**
     * 获得短信渠道列表
     *
     * @param ids 编号
     * @return 短信渠道列表
     */
    List<SmsChannelDO> getSmsChannelList(Collection<Long> ids);

    /**
     * 获得所有短信渠道列表
     *
     * @return 短信渠道列表
     */
    List<SmsChannelDO> getSmsChannelList();

    /**
     * 获得短信渠道分页
     *
     * @param pageReqVO 分页查询
     * @return 短信渠道分页
     */
    PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO);
}
