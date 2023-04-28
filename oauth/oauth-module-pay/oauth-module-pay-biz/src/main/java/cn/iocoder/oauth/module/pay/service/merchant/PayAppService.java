package cn.iocoder.oauth.module.pay.service.merchant;

import cn.iocoder.oauth.framework.common.exception.ServiceException;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppPageReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppUpdateReqVO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import com.sun.org.apache.xpath.internal.domapi.XPathStylesheetDOM3Exception;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 支付应用信息 Service 接口
 * @author xiaoxu123
 */
public interface PayAppService {

    /**
     *创建支付应用信息
     * @param createReqVO
     * @return Long
     */
    Long createApp(@Validated PayAppCreateReqVO createReqVO);

    /**
     * 更新支付应用信息
     *
     * @param updateReqVO 更新信息
     */
    void updateApp(@Valid PayAppUpdateReqVO updateReqVO);

    /**
     * 获得支付应用信息
     *
     * @param id 编号
     * @return 支付应用信息
     */
    PayAppDO getApp(Long id);

    /**
     * 获得支付应用信息列表
     *
     * @param ids 编号
     * @return 支付应用信息列表
     */
    List<PayAppDO> getAppList(Collection<Long> ids);

    /**
     * 获得支付应用信息分页
     *
     * @param pageReqVO 分页查询
     * @return 支付应用信息分页
     */
    PageResult<PayAppDO> getAppPage(PayAppPageReqVO pageReqVO);

    /**
     * 获得指定编号的商户 Map
     *
     * @param appIdList 应用编号集合
     * @return 商户 Map
     */
    default
    Map<Long, PayAppDO> getAppMap(Collection<Long> appIdList) {
        List<PayAppDO> list =  this.getAppList(appIdList);
        return CollectionUtils.convertMap(list, PayAppDO::getId);
    }

    /**
     * 支付应用的合法性
     *
     * 如果不合法，抛出 {@link ServiceException} 业务异常
     *
     * @param id 应用编号
     * @return 应用信息
     */
    PayAppDO validPayApp(Long id);



}
