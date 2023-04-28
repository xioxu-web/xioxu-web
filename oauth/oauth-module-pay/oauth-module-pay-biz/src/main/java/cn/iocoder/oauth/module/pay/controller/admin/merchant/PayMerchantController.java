package cn.iocoder.oauth.module.pay.controller.admin.merchant;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantCreateReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantPageReqVO;
import cn.iocoder.oauth.module.pay.controller.admin.merchant.vo.merchant.PayMerchantRespVO;
import cn.iocoder.oauth.module.pay.convert.merchant.PayMerchantConvert;
import cn.iocoder.oauth.module.pay.dal.dataobject.merchant.PayMerchantDO;
import cn.iocoder.oauth.module.pay.service.merchant.PayMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author xiaoxu123
 */
@Api(tags = "支付商户信息")
@RestController
@RequestMapping("/pay/merchant")
@Validated
public class PayMerchantController {

    @Resource
    private PayMerchantService merchantService;

    @PostMapping("/create")
    @ApiOperation("创建支付商户信息")
    @PreAuthorize("@ss.hasPermission('pay:merchant:create')")
    public CommonResult<Long> createMerchant(@Valid @RequestBody PayMerchantCreateReqVO createReqVO) {
        return success(merchantService.createMerchant(createReqVO));
    }

    @GetMapping("/get")
    @ApiOperation("获得支付商户信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('pay:merchant:query')")
    public CommonResult<PayMerchantRespVO> getMerchant(@RequestParam("id") Long id) {
        PayMerchantDO merchant = merchantService.getMerchant(id);
        return success(PayMerchantConvert.INSTANCE.convert(merchant));
    }

    @GetMapping("/list")
    @ApiOperation("获得支付商户信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('pay:merchant:query')")
    public CommonResult<List<PayMerchantRespVO>> getMerchantList(@RequestParam("ids") Collection<Long> ids) {
        List<PayMerchantDO> list = merchantService.getMerchantList(ids);
        return success(PayMerchantConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得支付商户信息分页")
    @PreAuthorize("@ss.hasPermission('pay:merchant:query')")
    public CommonResult<PageResult<PayMerchantRespVO>> getMerchantPage(@Valid PayMerchantPageReqVO pageVO) {
        PageResult<PayMerchantDO> pageResult = merchantService.getMerchantPage(pageVO);
        return success(PayMerchantConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-by-name")
    @ApiOperation("根据商户名称获得支付商户信息列表")
    @ApiImplicitParam(name = "name", value = "商户名称", example = "芋道", dataTypeClass = String.class)
    @PreAuthorize("@ss.hasPermission('pay:merchant:query')")
    public CommonResult<List<PayMerchantRespVO>> getMerchantListByName(@RequestParam(required = false) String name) {
        List<PayMerchantDO> merchantListDO = merchantService.getMerchantListByName(name);
        return success(PayMerchantConvert.INSTANCE.convertList(merchantListDO));
    }

}
