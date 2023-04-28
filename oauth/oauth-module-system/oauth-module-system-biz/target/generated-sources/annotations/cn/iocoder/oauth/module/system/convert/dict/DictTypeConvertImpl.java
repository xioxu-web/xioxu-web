package cn.iocoder.oauth.module.system.convert.dict;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictTypeDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-26T17:05:15+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class DictTypeConvertImpl implements DictTypeConvert {

    @Override
    public PageResult<DictTypeRespVO> convertPage(PageResult<DictTypeDO> bean) {
        if ( bean == null ) {
            return null;
        }

        PageResult<DictTypeRespVO> pageResult = new PageResult<DictTypeRespVO>();

        pageResult.setList( dictTypeDOListToDictTypeRespVOList( bean.getList() ) );
        pageResult.setTotal( bean.getTotal() );

        return pageResult;
    }

    @Override
    public DictTypeRespVO convert(DictTypeDO bean) {
        if ( bean == null ) {
            return null;
        }

        DictTypeRespVO dictTypeRespVO = new DictTypeRespVO();

        dictTypeRespVO.setName( bean.getName() );
        dictTypeRespVO.setStatus( bean.getStatus() );
        dictTypeRespVO.setRemark( bean.getRemark() );
        dictTypeRespVO.setId( bean.getId() );
        dictTypeRespVO.setType( bean.getType() );
        dictTypeRespVO.setCreateTime( bean.getCreateTime() );

        return dictTypeRespVO;
    }

    @Override
    public DictTypeDO convert(DictTypeCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        DictTypeDO dictTypeDO = new DictTypeDO();

        dictTypeDO.setName( bean.getName() );
        dictTypeDO.setType( bean.getType() );
        dictTypeDO.setStatus( bean.getStatus() );
        dictTypeDO.setRemark( bean.getRemark() );

        return dictTypeDO;
    }

    @Override
    public DictTypeDO convert(DictTypeUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        DictTypeDO dictTypeDO = new DictTypeDO();

        dictTypeDO.setId( bean.getId() );
        dictTypeDO.setName( bean.getName() );
        dictTypeDO.setStatus( bean.getStatus() );
        dictTypeDO.setRemark( bean.getRemark() );

        return dictTypeDO;
    }

    @Override
    public List<DictTypeSimpleRespVO> convertList(List<DictTypeDO> list) {
        if ( list == null ) {
            return null;
        }

        List<DictTypeSimpleRespVO> list1 = new ArrayList<DictTypeSimpleRespVO>( list.size() );
        for ( DictTypeDO dictTypeDO : list ) {
            list1.add( dictTypeDOToDictTypeSimpleRespVO( dictTypeDO ) );
        }

        return list1;
    }

    protected List<DictTypeRespVO> dictTypeDOListToDictTypeRespVOList(List<DictTypeDO> list) {
        if ( list == null ) {
            return null;
        }

        List<DictTypeRespVO> list1 = new ArrayList<DictTypeRespVO>( list.size() );
        for ( DictTypeDO dictTypeDO : list ) {
            list1.add( convert( dictTypeDO ) );
        }

        return list1;
    }

    protected DictTypeSimpleRespVO dictTypeDOToDictTypeSimpleRespVO(DictTypeDO dictTypeDO) {
        if ( dictTypeDO == null ) {
            return null;
        }

        DictTypeSimpleRespVO dictTypeSimpleRespVO = new DictTypeSimpleRespVO();

        dictTypeSimpleRespVO.setId( dictTypeDO.getId() );
        dictTypeSimpleRespVO.setName( dictTypeDO.getName() );
        dictTypeSimpleRespVO.setType( dictTypeDO.getType() );

        return dictTypeSimpleRespVO;
    }
}
