package cn.iocoder.oauth.module.system.service.dict.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.oauth.framework.common.enums.CommonStatusEnum;
import cn.iocoder.oauth.framework.common.util.collection.CollectionUtils;
import cn.iocoder.oauth.framework.dict.core.dto.DictDataRespDTO;
import cn.iocoder.oauth.module.system.controller.admin.dict.vo.data.DictDataExportReqVO;
import cn.iocoder.oauth.module.system.convert.dict.DictDataConvert;
import cn.iocoder.oauth.module.system.dal.dataobject.dict.DictDataDO;
import cn.iocoder.oauth.module.system.dal.mysql.dict.DictDataMapper;
import cn.iocoder.oauth.module.system.service.dict.DictDataService;
import cn.iocoder.oauth.module.system.service.dict.DictTypeService;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static cn.iocoder.oauth.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.oauth.module.system.enums.ErrorCodeConstants.*;

/**
 * 字典数据 Service 实现类
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<DictDataDO> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(DictDataDO::getDictType)
            .thenComparingInt(DictDataDO::getSort);

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    /**
     * 字典数据缓存，第二个 key 使用 label
     *
     * key1：字典类型 dictType
     * key2：字典标签 label
     */
    private ImmutableTable<String, String, DictDataDO> labelDictDataCache;
    /**
     * 字典数据缓存，第二个 key 使用 value
     *
     * key1：字典类型 dictType
     * key2：字典值 value
     */
    private ImmutableTable<String, String, DictDataDO> valueDictDataCache;
    /**
     * 缓存字典数据的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Override
    @PostConstruct
    public synchronized void initLocalCache() {
        // 获取字典数据列表，如果有更新
        List<DictDataDO> dataList = loadDictDataIfUpdate(maxUpdateTime);
        if (CollUtil.isEmpty(dataList)) {
            return;
        }

        // 构建缓存
        ImmutableTable.Builder<String, String, DictDataDO> labelDictDataBuilder = ImmutableTable.builder();
        ImmutableTable.Builder<String, String, DictDataDO> valueDictDataBuilder = ImmutableTable.builder();
        dataList.forEach(dictData -> {
            labelDictDataBuilder.put(dictData.getDictType(), dictData.getLabel(), dictData);
            valueDictDataBuilder.put(dictData.getDictType(), dictData.getValue(), dictData);
        });
        labelDictDataCache = labelDictDataBuilder.build();
        valueDictDataCache = valueDictDataBuilder.build();
        maxUpdateTime = CollectionUtils.getMaxValue(dataList, DictDataDO::getUpdateTime);
        log.info("[initLocalCache][缓存字典数据，数量为:{}]", dataList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCache();
    }

    @Override
    public List<DictDataDO> getDictDatas() {
        List<DictDataDO> list = dictDataMapper.selectList();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }


    @Override
    public List<DictDataDO> getDictDatas(DictDataExportReqVO reqVO) {
        List<DictDataDO> list = dictDataMapper.selectList( reqVO );
        list.sort( COMPARATOR_TYPE_AND_SORT );
        return list;
    }

    @Override
    public
    DictDataRespDTO getDictDataFromCache(String type, String value) {
        return DictDataConvert.INSTANCE.convert02(valueDictDataCache.get(type, value));
    }

    @Override
    public DictDataRespDTO parseDictDataFromCache(String type, String label) {
        return DictDataConvert.INSTANCE.convert02(labelDictDataCache.get(type, label));
    }

    @Override
    public List<DictDataRespDTO> listDictDatasFromCache(String type) {
        return DictDataConvert.INSTANCE.convertList03(labelDictDataCache.row(type).values());
    }

    @VisibleForTesting
    public void checkDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictDataDO dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    /**
     * 如果字典数据发生变化，从数据库中获取最新的全量字典数据。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前字典数据的最大更新时间
     * @return 字典数据列表
     */
    private List<DictDataDO> loadDictDataIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadDictDataIfUpdate][首次加载全量字典数据]");
        } else { // 判断数据库中是否有更新的字典数据
            if (dictDataMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadDictDataIfUpdate][增量加载全量字典数据]");
        }
        // 第二步，如果有更新，则从数据库加载所有字典数据
        return dictDataMapper.selectList();
    }


}
