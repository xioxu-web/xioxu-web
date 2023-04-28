package io.microservice.oauth2.cloud.admin.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.microservice.oauth2.cloud.admin.api.entity.SysOauthClientDetails;

import java.io.Serializable;

/**
 * @author xiaoxu123
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

    /**
     *通过id删除客户端
     * @param id
     * @return
     */
    Boolean removeClientDetailsById(String id);


    /**
     *修改客户端信息
     * @param clientDetails
     * @return
     */
    Boolean updateClientDetails(SysOauthClientDetails clientDetails);


    /**
     * 清除客户端缓存
     */
    void clearClientCache();


}
