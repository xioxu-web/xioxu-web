package cn.iocoder.oauth.module.system.convert.dict;

import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictTypeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);

    PageResult<DictTypeRespVO> convertPage(PageResult<DictTypeDO> bean);

    DictTypeRespVO convert(DictTypeDO bean);

    DictTypeDO convert(DictTypeCreateReqVO bean);

    DictTypeDO convert(DictTypeUpdateReqVO bean);

    List<DictTypeSimpleRespVO> convertList(List<DictTypeDO> list);


}
