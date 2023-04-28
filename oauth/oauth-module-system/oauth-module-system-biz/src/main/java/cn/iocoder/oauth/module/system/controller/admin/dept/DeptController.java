package cn.iocoder.oauth.module.system.controller.admin.dept;

import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.iocoder.oauth.module.system.convert.dept.DeptConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import cn.iocoder.oauth.module.system.service.dept.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 部门")
@RestController
@RequestMapping("/system/dept")
@Validated
public class DeptController {
    @Resource
    private DeptService deptService;
    /**
     * 获取部门列表信息
     */
   @GetMapping("/list")
   @ApiOperation("获取部门列表")
   @PreAuthorize("@ss.hasPermission('system:dept:query')")
   public CommonResult<List<DeptRespVO>> listDepts(DeptListReqVO reqVO){
       List<DeptDO> list = deptService.getSimpleDepts( reqVO );
       list.sort( Comparator.comparing(DeptDO::getSort));
       return success(DeptConvert.INSTANCE.convertList(list));
   }

   @GetMapping("/list-all-simple")
   @ApiOperation(value = "获取部门精简信息列表", notes = "只包含被开启的部门，主要用于前端的下拉选项")
   public CommonResult<List<DeptSimpleRespVO>> getSimpleDepts(){
       // 获得部门列表，只要开启状态的
       DeptListReqVO reqVO = new DeptListReqVO();
       reqVO.setStatus( CommonStatusEnum.ENABLE.getStatus());
       List<DeptDO> list = deptService.getSimpleDepts( reqVO );
       // 排序后，返回给前端
       list.sort(Comparator.comparing(DeptDO::getSort));
       return success(DeptConvert.INSTANCE.convertList02(list));
   }

    @GetMapping("/get")
    @ApiOperation("获得部门信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
        return success(DeptConvert.INSTANCE.convert(deptService.getDept(id)));
    }



}
