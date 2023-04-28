package cn.iocoder.oauth.module.system.convert.dept;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.PostDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    List<PostSimpleRespVO> convertList02(List<PostDO> list);

    PageResult<PostRespVO> convertPage(PageResult<PostDO> page);

    PostRespVO convert(PostDO id);

    PostDO convert(PostCreateReqVO bean);

    PostDO convert(PostUpdateReqVO reqVO);

}
