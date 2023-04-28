package cn.iocoder.oauth.module.pay.controller.admin.merchant;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.pay.core.enums.PayChannelEnum;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.app.*;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantPageReqVO;
import cn.iocoder.oauth.module.pay.convert.app.PayAppConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayAppDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayChannelDO;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import cn.iocoder.oauth.module.pay.service.merchant.PayAppService;
import cn.iocoder.oauth.module.pay.service.merchant.PayChannelService;
import cn.iocoder.oauth.module.pay.service.merchant.PayMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.*;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author admin
 */
@Slf4j
@Api(tags = "管理后台 - 支付应用信息")
@RestController
@RequestMapping("/pay/app")
@Validated
public class PayAppController {

    @Resource
    private PayAppService appService;

    @Resource
    private PayMerchantService merchantService;

    @Resource
    private PayChannelService channelService;

    @PostMapping("/create")
    @ApiOperation("创建支付应用信息")
    @PreAuthorize("@ss.hasPermission('pay:app:create')")
    public CommonResult<Long> createApp(@Valid @RequestBody PayAppCreateReqVO createReqVO) {
        return success(appService.createApp(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新支付应用信息")
    @PreAuthorize("@ss.hasPermission('pay:app:update')")
    public CommonResult<Boolean> updateApp(@Valid @RequestBody PayAppUpdateReqVO updateReqVO) {
        appService.updateApp(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得支付应用信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('pay:app:query')")
    public CommonResult<PayAppRespVO> getApp(@RequestParam("id") Long id) {
        PayAppDO app = appService.getApp(id);
        return success(PayAppConvert.INSTANCE.convert(app));
    }

    @GetMapping("/list")
    @ApiOperation("获得支付应用信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('pay:app:query')")
    public CommonResult<List<PayAppRespVO>> getAppList(@RequestParam("ids") Collection<Long> ids) {
        List<PayAppDO> list = appService.getAppList(ids);
        return success(PayAppConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得支付应用信息分页")
    @PreAuthorize("@ss.hasPermission('pay:app:query')")
    public CommonResult<PageResult<PayAppPageItemRespVO>> getAppPage(@Valid PayAppPageReqVO pageVO) {
        // 得到应用分页列表
        PageResult<PayAppDO> pageResult = appService.getAppPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        // 得到所有的应用编号，查出所有的渠道
        Collection<Long> payAppIds = CollectionUtils.convertList(pageResult.getList(), PayAppDO::getId);
        List<PayChannelDO> channels = channelService.getChannelListByAppIds(payAppIds);
        Iterator<PayChannelDO> iterator = channels.iterator();

        // 得到所有的商户信息
        Collection<Long> merchantIds = CollectionUtils.convertList(pageResult.getList(), PayAppDO::getMerchantId);
        Map<Long, PayMerchantDO> deptMap = merchantService.getMerchantMap(merchantIds);

        // 利用反射将渠道数据复制到返回的数据结构中去
        List<PayAppPageItemRespVO> appList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(app -> {
            // 写入应用信息的数据
            PayAppPageItemRespVO respVO = PayAppConvert.INSTANCE.pageConvert(app);
            // 写入商户的数据
            respVO.setPayMerchant(PayAppConvert.INSTANCE.convert(deptMap.get(app.getMerchantId())));
            // 写入支付渠道信息的数据
            Set<String> channelCodes = new HashSet<>(PayChannelEnum.values().length);
            while (iterator.hasNext()) {
                PayChannelDO channelDO = iterator.next();
                if (channelDO.getAppId().equals(app.getId())) {
                    channelCodes.add(channelDO.getCode());
                    iterator.remove();
                }
            }
            respVO.setChannelCodes(channelCodes);
            appList.add(respVO);
        });

        return success(new PageResult<>(appList, pageResult.getTotal()));
    }

}
