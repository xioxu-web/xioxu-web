package cn.iocoder.oauth.module.system.service.dict.Impl;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.oauth.module.system.service.dict.DictDataService;
import cn.iocoder.oauth.module.system.service.dict.DictTypeService;
import cn.iocoder.oauth.framework.common.pojo.PageResult;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeExportReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictTypeDO;
import cn.iocoder.oauth.module.system.dal.mysql.dict.DictTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 字典类型 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO reqVO) {
        return dictTypeMapper.selectPage(reqVO);
    }

    @Override
    public List<DictTypeDO> getDictTypeList(DictTypeExportReqVO reqVO) {
        return dictTypeMapper.selectList(reqVO);
    }

    @Override
    public DictTypeDO getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictTypeDO getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }


    @Override
    public
    Long createDictType(DictTypeCreateReqVO reqVO) {
        return null;
    }

    @Override
    public
    void updateDictType(DictTypeUpdateReqVO reqVO) {

    }

    @Override
    public void deleteDictType(Long id) {

    }


    @Override
    public List<DictTypeDO> getDictTypeList() {
        return dictTypeMapper.selectList();
    }




}
