package cn.iocoder.oauth.module.system.controller.admin.dict;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import cn.iocoder.oauth.module.system.convert.dict.DictDataConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.oauth.module.system.service.dict.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Api(tags = "管理后台 - 字典数据")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
    //无需添加权限认证，因为前端全局都需要
    public CommonResult<List<DictDataSimpleRespVO>> getSimpleDictDatas() {
        List<DictDataDO> list = dictDataService.getDictDatas();
        return CommonResult.success( DictDataConvert.INSTANCE.convertList(list));
    }

}
