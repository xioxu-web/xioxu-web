package cn.iocoder.oauth.module.system.controller.admin.dict;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeSimpleRespVO;
import cn.iocoder.oauth.module.system.convert.dict.DictTypeConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.oauth.module.system.service.dict.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;


    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典类型列表", notes = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    // 无需添加权限认证，因为前端全局都需要
    public
    CommonResult<List<DictTypeSimpleRespVO>> listSimpleDictTypes() {
        List<DictTypeDO> list = dictTypeService.getDictTypeList();
        return success(DictTypeConvert.INSTANCE.convertList(list));
    }


}
