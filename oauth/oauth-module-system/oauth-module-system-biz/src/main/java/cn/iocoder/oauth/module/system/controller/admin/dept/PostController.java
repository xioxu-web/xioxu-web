package cn.iocoder.oauth.module.system.controller.admin.dept;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.pojo.CommonResult;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.*;
import cn.iocoder.oauth.module.system.convert.dept.PostConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.PostDO;
import cn.iocoder.oauth.module.system.service.dept.PostService;
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
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.oauth.framework.common.pojo.CommonResult.success;
import static cn.iocoder.oauth.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

/**
 * @author xiaoxu123
 */
@Api(tags = "管理后台 - 岗位")
@RestController
@RequestMapping("/system/post")
@Validated
public class PostController {

    @Resource
    private  PostService postService;

    @PostMapping("/create")
    @ApiOperation("创建岗位")
    @PreAuthorize("@ss.hasPermission('system:post:create')")
    public CommonResult<Long> createPost(@Valid @RequestBody PostCreateReqVO reqVO) {
        Long postId = postService.createPost(reqVO);
        return success(postId);
    }

    @GetMapping("/get")
    @ApiOperation("获得岗位信息")
    @ApiImplicitParam(name = "id", value = "岗位编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CommonResult<PostRespVO> getPost(@RequestParam("id") Long id){
       return success(PostConvert.INSTANCE.convert(postService.getPost(id)));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取岗位精简信息列表", notes = "只包含被开启的岗位，主要用于前端的下拉选项")
    public CommonResult<List<PostSimpleRespVO>> getSimplePosts(){
        // 获得岗位列表，只要开启状态的
        List<PostDO> list = postService.getPosts( null, CollectionUtils.singleton( CommonStatusEnum.ENABLE.getStatus() ) );
        // 排序后，返回给前端
        list.sort( Comparator.comparing(PostDO::getStatus));
        return success(PostConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得岗位分页列表")
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public CommonResult<PageResult<PostRespVO>>  getPostPage(@Valid PostPageReqVO reqVO){
      return success(PostConvert.INSTANCE.convertPage(postService.getPostPage(reqVO)));
    }


}
