package cn.iocoder.oauth.module.system.convert.dict;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.framework.dict.core.dto.DictDataRespDTO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictDataDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    List<DictDataSimpleRespVO> convertList(List<DictDataDO> list);

    DictDataRespVO convert(DictDataDO bean);

    PageResult<DictDataRespVO> convertPage(PageResult<DictDataDO> page);

    DictDataDO convert(DictDataUpdateReqVO bean);

    DictDataDO convert(DictDataCreateReqVO bean);

    DictDataRespDTO convert02(DictDataDO bean);

    List<DictDataRespDTO> convertList03(Collection<DictDataDO> list);

}