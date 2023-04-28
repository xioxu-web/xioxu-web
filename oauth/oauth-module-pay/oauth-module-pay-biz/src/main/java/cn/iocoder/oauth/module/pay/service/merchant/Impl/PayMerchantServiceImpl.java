package cn.iocoder.oauth.module.pay.service.merchant.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantPageReqVO;
import cn.iocoder.oauth.module.pay.convert.merchant.PayMerchantConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import cn.iocoder.oauth.module.pay.dal.mysql.merchant.PayAppMapper;
import cn.iocoder.oauth.module.pay.dal.mysql.merchant.PayMerchantMapper;
import cn.iocoder.oauth.module.pay.service.merchant.PayMerchantService;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants.PAY_MERCHANT_EXIST_APP_CANT_DELETE;
import static cn.iocoder.oauth.module.pay.enums.ErrorCodeConstants.PAY_MERCHANT_NOT_EXISTS;


/**
 * 支付商户信息 Service 实现类
 *
 * @author admin
 */
@Service
@Validated
public class PayMerchantServiceImpl implements PayMerchantService {

    @Resource
    private PayMerchantMapper merchantMapper;

    @Resource
    private PayAppMapper appMapper;

    @Override
    public Long createMerchant(PayMerchantCreateReqVO createReqVO) {
        // 插入
        PayMerchantDO merchant = PayMerchantConvert.INSTANCE.convert(createReqVO);
        merchant.setNo(this.generateMerchantNo());
        merchantMapper.insert(merchant);
        // 返回
        return merchant.getId();
    }

    @Override
    public PayMerchantDO getMerchant(Long id) {
        return merchantMapper.selectById(id);
    }


    @Override
    public List<PayMerchantDO> getMerchantList(Collection<Long> ids) {
        return merchantMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PayMerchantDO> getMerchantPage(PayMerchantPageReqVO pageReqVO) {
        return merchantMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PayMerchantDO> getMerchantListByName(String merchantName) {
        return this.merchantMapper.getMerchantListByName(merchantName);
    }

    @VisibleForTesting
    public void checkMerchantExists(Long id) {
        if (id == null) {
            return;
        }
        PayMerchantDO merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            throw exception(PAY_MERCHANT_NOT_EXISTS);
        }
    }

    /**
     * 校验商户是否存在
     *
     * @param id 商户 ID
     */
    private void validateMerchantExists(Long id) {
        if (ObjectUtil.isNull(merchantMapper.selectById(id))) {
            throw exception(PAY_MERCHANT_NOT_EXISTS);
        }
    }

    /**
     * 校验商户是否还存在支付应用
     *
     * @param id 商户ID
     */
    private void validateAppExists(Long id) {
        if (appMapper.selectCount(id) > 0) {
            throw exception(PAY_MERCHANT_EXIST_APP_CANT_DELETE);
        }
    }

    // TODO @芋艿：后续增加下合适的算法
    /**
     * 根据年月日时分秒毫秒生成商户号
     *
     * @return 商户号
     */
    private String generateMerchantNo() {
        return "M" + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmssSSS");
    }

}
