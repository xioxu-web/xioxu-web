package cn.iocoder.oauth.module.system.service.dict;
import cn.iocoder.oauth.framework.dict.core.service.DictDataFrameworkService;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataExportReqVO;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictDataDO;

import java.util.List;

/**
 * 字典数据 Service 接口
 *
 * @author ruoyi
 */
public interface DictDataService extends DictDataFrameworkService {

    /**
     * 初始化字典数据的本地缓存
     */
    void initLocalCache();

    /**
     * 获得字典数据列表
     *
     * @return 字典数据全列表
     */
    List<DictDataDO> getDictDatas();

    /**
     * 获得字典数据列表
     *
     * @param reqVO 列表请求
     * @return 字典数据列表
     */
    List<DictDataDO> getDictDatas(DictDataExportReqVO reqVO);

}
