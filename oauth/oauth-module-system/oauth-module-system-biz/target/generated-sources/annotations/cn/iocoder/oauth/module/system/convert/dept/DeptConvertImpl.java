package cn.iocoder.oauth.module.system.convert.dept;

import cn.iocoder.oauth.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dept.vo.dept.DeptUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dept.DeptDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class DeptConvertImpl implements DeptConvert {

    @Override
    public List<DeptRespVO> convertList(List<DeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<DeptRespVO> list1 = new ArrayList<DeptRespVO>( list.size() );
        for ( DeptDO deptDO : list ) {
            list1.add( convert( deptDO ) );
        }

        return list1;
    }

    @Override
    public List<DeptSimpleRespVO> convertList02(List<DeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<DeptSimpleRespVO> list1 = new ArrayList<DeptSimpleRespVO>( list.size() );
        for ( DeptDO deptDO : list ) {
            list1.add( deptDOToDeptSimpleRespVO( deptDO ) );
        }

        return list1;
    }

    @Override
    public DeptRespVO convert(DeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        DeptRespVO deptRespVO = new DeptRespVO();

        deptRespVO.setName( bean.getName() );
        deptRespVO.setParentId( bean.getParentId() );
        deptRespVO.setSort( bean.getSort() );
        deptRespVO.setLeaderUserId( bean.getLeaderUserId() );
        deptRespVO.setPhone( bean.getPhone() );
        deptRespVO.setEmail( bean.getEmail() );
        deptRespVO.setId( bean.getId() );
        deptRespVO.setStatus( bean.getStatus() );
        deptRespVO.setCreateTime( bean.getCreateTime() );

        return deptRespVO;
    }

    @Override
    public DeptDO convert(DeptCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        DeptDO deptDO = new DeptDO();

        deptDO.setName( bean.getName() );
        deptDO.setParentId( bean.getParentId() );
        deptDO.setSort( bean.getSort() );
        deptDO.setLeaderUserId( bean.getLeaderUserId() );
        deptDO.setPhone( bean.getPhone() );
        deptDO.setEmail( bean.getEmail() );
        deptDO.setStatus( bean.getStatus() );

        return deptDO;
    }

    @Override
    public DeptDO convert(DeptUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        DeptDO deptDO = new DeptDO();

        deptDO.setId( bean.getId() );
        deptDO.setName( bean.getName() );
        deptDO.setParentId( bean.getParentId() );
        deptDO.setSort( bean.getSort() );
        deptDO.setLeaderUserId( bean.getLeaderUserId() );
        deptDO.setPhone( bean.getPhone() );
        deptDO.setEmail( bean.getEmail() );
        deptDO.setStatus( bean.getStatus() );

        return deptDO;
    }

    @Override
    public List<DeptRespDTO> convertList03(List<DeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<DeptRespDTO> list1 = new ArrayList<DeptRespDTO>( list.size() );
        for ( DeptDO deptDO : list ) {
            list1.add( convert03( deptDO ) );
        }

        return list1;
    }

    @Override
    public DeptRespDTO convert03(DeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        DeptRespDTO deptRespDTO = new DeptRespDTO();

        deptRespDTO.setId( bean.getId() );
        deptRespDTO.setName( bean.getName() );
        deptRespDTO.setParentId( bean.getParentId() );
        deptRespDTO.setLeaderUserId( bean.getLeaderUserId() );
        deptRespDTO.setStatus( bean.getStatus() );

        return deptRespDTO;
    }

    @Override
    public Map<Long, DeptRespDTO> convertMap(Map<Long, DeptDO> map) {
        if ( map == null ) {
            return null;
        }

        Map<Long, DeptRespDTO> map1 = new HashMap<Long, DeptRespDTO>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<Long, DeptDO> entry : map.entrySet() ) {
            Long key = entry.getKey();
            DeptRespDTO value = convert03( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected DeptSimpleRespVO deptDOToDeptSimpleRespVO(DeptDO deptDO) {
        if ( deptDO == null ) {
            return null;
        }

        DeptSimpleRespVO deptSimpleRespVO = new DeptSimpleRespVO();

        deptSimpleRespVO.setId( deptDO.getId() );
        deptSimpleRespVO.setName( deptDO.getName() );
        deptSimpleRespVO.setParentId( deptDO.getParentId() );

        return deptSimpleRespVO;
    }
}
