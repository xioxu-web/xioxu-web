package com.example.mybatis_plus_demo.baseMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaoxu123
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    /**
     *封装通用的方法
     *
     */
   Integer deleteAll();

   int insertAll();

    int sqlInsertAllBatch(@Param("list") List<T> batchList);

    int insertBatchSomeColumn(List<T> list);

    int deleteByIdWithFill(T entity);

    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);


}
