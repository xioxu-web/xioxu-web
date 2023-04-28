package cn.iocoder.oauth.module.system.convert.permission;

import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.permission.vo.role.RoleUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.permission.RoleDO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class RoleConvertImpl implements RoleConvert {

    @Override
    public RoleDO convert(RoleUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        RoleDO roleDO = new RoleDO();

        roleDO.setId( bean.getId() );
        roleDO.setName( bean.getName() );
        roleDO.setCode( bean.getCode() );
        roleDO.setSort( bean.getSort() );
        roleDO.setRemark( bean.getRemark() );

        return roleDO;
    }

    @Override
    public RoleRespVO convert(RoleDO bean) {
        if ( bean == null ) {
            return null;
        }

        RoleRespVO roleRespVO = new RoleRespVO();

        roleRespVO.setName( bean.getName() );
        roleRespVO.setCode( bean.getCode() );
        roleRespVO.setSort( bean.getSort() );
        roleRespVO.setRemark( bean.getRemark() );
        roleRespVO.setId( bean.getId() );
        roleRespVO.setDataScope( bean.getDataScope() );
        Set<Long> set = bean.getDataScopeDeptIds();
        if ( set != null ) {
            roleRespVO.setDataScopeDeptIds( new HashSet<Long>( set ) );
        }
        roleRespVO.setStatus( bean.getStatus() );
        roleRespVO.setType( bean.getType() );
        roleRespVO.setCreateTime( bean.getCreateTime() );

        return roleRespVO;
    }

    @Override
    public RoleDO convert(RoleCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        RoleDO roleDO = new RoleDO();

        roleDO.setName( bean.getName() );
        roleDO.setCode( bean.getCode() );
        roleDO.setSort( bean.getSort() );
        roleDO.setRemark( bean.getRemark() );

        return roleDO;
    }

    @Override
    public List<RoleSimpleRespVO> convertList02(List<RoleDO> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleSimpleRespVO> list1 = new ArrayList<RoleSimpleRespVO>( list.size() );
        for ( RoleDO roleDO : list ) {
            list1.add( roleDOToRoleSimpleRespVO( roleDO ) );
        }

        return list1;
    }

    protected RoleSimpleRespVO roleDOToRoleSimpleRespVO(RoleDO roleDO) {
        if ( roleDO == null ) {
            return null;
        }

        RoleSimpleRespVO roleSimpleRespVO = new RoleSimpleRespVO();

        roleSimpleRespVO.setId( roleDO.getId() );
        roleSimpleRespVO.setName( roleDO.getName() );

        return roleSimpleRespVO;
    }
}
