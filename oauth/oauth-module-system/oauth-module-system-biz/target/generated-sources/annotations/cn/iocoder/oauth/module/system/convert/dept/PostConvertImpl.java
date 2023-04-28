package cn.iocoder.oauth.module.system.convert.dept;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.post.PostUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.PostDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:16+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class PostConvertImpl implements PostConvert {

    @Override
    public List<PostSimpleRespVO> convertList02(List<PostDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PostSimpleRespVO> list1 = new ArrayList<PostSimpleRespVO>( list.size() );
        for ( PostDO postDO : list ) {
            list1.add( postDOToPostSimpleRespVO( postDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<PostRespVO> convertPage(PageResult<PostDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<PostRespVO> pageResult = new PageResult<PostRespVO>();

        pageResult.setList( postDOListToPostRespVOList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );

        return pageResult;
    }

    @Override
    public PostRespVO convert(PostDO id) {
        if ( id == null ) {
            return null;
        }

        PostRespVO postRespVO = new PostRespVO();

        postRespVO.setName( id.getName() );
        postRespVO.setCode( id.getCode() );
        postRespVO.setSort( id.getSort() );
        postRespVO.setStatus( id.getStatus() );
        postRespVO.setRemark( id.getRemark() );
        postRespVO.setId( id.getId() );
        postRespVO.setCreateTime( id.getCreateTime() );

        return postRespVO;
    }

    @Override
    public PostDO convert(PostCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        PostDO postDO = new PostDO();

        postDO.setName( bean.getName() );
        postDO.setCode( bean.getCode() );
        postDO.setSort( bean.getSort() );
        postDO.setStatus( bean.getStatus() );
        postDO.setRemark( bean.getRemark() );

        return postDO;
    }

    @Override
    public PostDO convert(PostUpdateReqVO reqVO) {
        if ( reqVO == null ) {
            return null;
        }

        PostDO postDO = new PostDO();

        postDO.setId( reqVO.getId() );
        postDO.setName( reqVO.getName() );
        postDO.setCode( reqVO.getCode() );
        postDO.setSort( reqVO.getSort() );
        postDO.setStatus( reqVO.getStatus() );
        postDO.setRemark( reqVO.getRemark() );

        return postDO;
    }

    protected PostSimpleRespVO postDOToPostSimpleRespVO(PostDO postDO) {
        if ( postDO == null ) {
            return null;
        }

        PostSimpleRespVO postSimpleRespVO = new PostSimpleRespVO();

        postSimpleRespVO.setId( postDO.getId() );
        postSimpleRespVO.setName( postDO.getName() );

        return postSimpleRespVO;
    }

    protected List<PostRespVO> postDOListToPostRespVOList(List<PostDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PostRespVO> list1 = new ArrayList<PostRespVO>( list.size() );
        for ( PostDO postDO : list ) {
            list1.add( convert( postDO ) );
        }

        return list1;
    }
}
