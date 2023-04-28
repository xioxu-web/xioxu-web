package cn.iocoder.oauth.module.pay.controller.admin.order;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.pay.core.enums.PayChannelEnum;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderDetailsRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderPageItemRespVO;
import cn.iocoder.oauth.module.pay.controller.admin.order.vo.PayOrderPageReqVO;
import cn.iocoder.oauth.module.pay.convert.order.PayOrderConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.iocoder.oauth.module.pay.service.merchant.PayAppService;
import cn.iocoder.oauth.module.pay.service.merchant.PayMerchantService;
import cn.iocoder.oauth.module.pay.service.order.PayOrderExtensionService;
import cn.iocoder.oauth.module.pay.service.order.PayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 支付订单")
@RestController
@RequestMapping("/pay/order")
@Validated
public class PayOrderController {

    @Resource
    private PayOrderService orderService;
    @Resource
    private PayOrderExtensionService orderExtensionService;
    @Resource
    private PayMerchantService merchantService;
    @Resource
    private PayAppService appService;


    /**
     *获取支付订单
     */
    @GetMapping("/get")
    @ApiOperation("获得支付订单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('pay:order:query')")
    public CommonResult<PayOrderDetailsRespVO> getOrder(@RequestParam("id") Long id) {
        PayOrderDO order = orderService.getOrder( id );
        if (Objects.isNull( order )) {
            return success( new PayOrderDetailsRespVO() );
        }
        PayMerchantDO merchantDO = merchantService.getMerchant( order.getMerchantId() );
        PayAppDO appDO = appService.getApp( order.getAppId() );
        PayChannelEnum channelEnum = PayChannelEnum.getByCode( order.getChannelCode() );
        PayOrderDetailsRespVO respVO = PayOrderConvert.INSTANCE.orderDetailConvert( order );
        respVO.setMerchantName( ObjectUtil.isNotNull( merchantDO ) ? merchantDO.getName() : "未知商户" );
        respVO.setAppName( ObjectUtil.isNotNull( appDO ) ? appDO.getName() : "未知应用" );
        respVO.setChannelCodeName( ObjectUtil.isNotNull( channelEnum ) ? channelEnum.getName() : "未知渠道" );

        PayOrderExtensionDO extensionDO = orderExtensionService.getOrderExtension( order.getSuccessExtensionId() );
        if (ObjectUtil.isNotNull( extensionDO )) {
            respVO.setPayOrderExtension( PayOrderConvert.INSTANCE.orderDetailExtensionConvert( extensionDO ) );
        }
        return success(respVO);

    }

    @GetMapping("/page")
    @ApiOperation("获得支付订单分页")
    @PreAuthorize("@ss.hasPermission('pay:order:query')")
    public CommonResult<PageResult<PayOrderPageItemRespVO>> getOrderPage(@Valid PayOrderPageReqVO pageVO) {
        PageResult<PayOrderDO> pageResult = orderService.getOrderPage(pageVO);
        if (CollectionUtil.isEmpty(pageResult.getList())) {
            return success( new PageResult<>( pageResult.getTotal() ) );
        }

        // 处理商户ID数据
        Map<Long, PayMerchantDO> merchantMap = merchantService.getMerchantMap(
                CollectionUtils.convertList(pageResult.getList(), PayOrderDO::getMerchantId));
        // 处理应用ID数据
        Map<Long, PayAppDO> appMap = appService.getAppMap(
                CollectionUtils.convertList(pageResult.getList(), PayOrderDO::getAppId));

        List<PayOrderPageItemRespVO> pageList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(c -> {
            PayMerchantDO merchantDO = merchantMap.get(c.getMerchantId());
            PayAppDO appDO = appMap.get(c.getAppId());
            PayChannelEnum channelEnum = PayChannelEnum.getByCode(c.getChannelCode());

            PayOrderPageItemRespVO orderItem = PayOrderConvert.INSTANCE.pageConvertItemPage(c);
            orderItem.setMerchantName(ObjectUtil.isNotNull(merchantDO) ? merchantDO.getName() : "未知商户");
            orderItem.setAppName(ObjectUtil.isNotNull(appDO) ? appDO.getName() : "未知应用");
            orderItem.setChannelCodeName(ObjectUtil.isNotNull(channelEnum) ? channelEnum.getName() : "未知渠道");
            pageList.add(orderItem);
        });
        return success(new PageResult<>(pageList, pageResult.getTotal()));
    }

}
