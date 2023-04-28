package io.microservice.oauth2.cloud.common.mybatis.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author xiaoxu123
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start insert fill ....");
        LocalDateTime now = LocalDateTime.now();

        fillValIfNullByName("createTime", now, metaObject, false);
        fillValIfNullByName("updateTime", now, metaObject, false);
        fillValIfNullByName("createBy", getUserName(), metaObject, false);
        fillValIfNullByName("updateBy", getUserName(), metaObject, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start update fill ....");
        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
        fillValIfNullByName("updateBy", getUserName(), metaObject, true);
    }

    private static void fillValIfNullByName(String fieldName,Object  fieldVal,MetaObject metaObject,boolean isCover){
       //没有getter方法
       if(!metaObject.hasGetter(fieldName)){
          return;
       }
        //如果用户有手动设置的值
        Object userSetValue = metaObject.getValue( fieldName );
        String setValueStr = StrUtil.str( userSetValue, Charset.defaultCharset() );
        if(StrUtil.isNotBlank(setValueStr) && !isCover){
           return;
        }
        // field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType( fieldName );
        if(ClassUtils.isAssignableValue(getterType,fieldVal)){
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    /**
     * 获取 spring security 当前的用户名
     * @return 当前用户名
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()) {
            return authentication.getName();
        }
        return null;
    }
}
