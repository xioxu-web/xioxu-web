package cn.iocoder.oauth.module.pay.service.merchant.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppPageReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.PayAppUpdateReqVO;
import cn.iocoder.oauth.module.pay.convert.app.PayAppConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import cn.iocoder.oauth.module.pay.dal.mysql.merchant.PayAppMapper;
import cn.iocoder.oauth.module.pay.dal.mysql.merchant.PayMerchantMapper;
import cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants;
import cn.iocoder.oauth.module.pay.service.merchant.PayAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants.PAY_APP_NOT_FOUND;

/**
 * @author xiaoxu123
 */
@Service
@Slf4j
public class PayAppServiceImpl implements PayAppService {

    @Resource
    private PayAppMapper appMapper;

    @Resource
    private PayMerchantMapper merchantMapper;

    @Override
    public Long createApp(PayAppCreateReqVO createReqVO) {
        PayAppDO payAppDO = PayAppConvert.INSTANCE.convert( createReqVO );
        appMapper.insert(payAppDO);
        return payAppDO.getId();
    }

    @Override
    public void updateApp(PayAppUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateAppExists(updateReqVO.getId());
        // 更新
        PayAppDO updateObj = PayAppConvert.INSTANCE.convert(updateReqVO);
        appMapper.updateById(updateObj);
    }

    private void validateAppExists(Long id) {
        if (appMapper.selectById(id) == null) {
            throw exception(PAY_APP_NOT_FOUND);
        }
    }

    @Override
    public PayAppDO getApp(Long id) {
        return appMapper.selectById(id);
    }

    @Override
    public List<PayAppDO> getAppList(Collection<Long> ids) {
        return appMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PayAppDO> getAppPage(PayAppPageReqVO pageReqVO) {
        Set<Long> merchantIdList = this.getMerchantCondition(pageReqVO.getMerchantName());
        if (StrUtil.isNotBlank(pageReqVO.getMerchantName()) && CollectionUtil.isEmpty(merchantIdList)) {
            return new PageResult<>();
        }
        return appMapper.selectPage(pageReqVO, merchantIdList);
    }

    /**
     * 获取商户编号集合，根据商户名称模糊查询得到所有的商户编号集合
     *
     * @param merchantName 商户名称
     * @return 商户编号集合
     */
    private
    Set<Long> getMerchantCondition(String merchantName) {
        if (StrUtil.isBlank(merchantName)) {
            return Collections.emptySet();
        }
        return convertSet(merchantMapper.getMerchantListByName(merchantName), PayMerchantDO::getId);
    }


    @Override
    public PayAppDO validPayApp(Long id) {
        PayAppDO app = appMapper.selectById(id);
        // 校验是否存在
        if (app == null) {
            throw exception( PAY_APP_NOT_FOUND);
        }
        // 校验是否禁用
        if (CommonStatusEnum.DISABLE.getStatus().equals(app.getStatus())) {
            throw exception(ErrorCodeConstants.PAY_APP_IS_DISABLE);
        }
        return app;
    }

}
