package io.microservices.shop.service.user.controller;

import io.microservices.shop.common.constant.StatusCode;
import io.microservices.shop.common.resp.Result;
import io.microservices.shop.service.user.api.domain.OauthClientDetails;
import io.microservices.shop.service.user.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaoxu123
 */
@RestController
@RequestMapping("/oauthClientDetails")
@CrossOrigin
public class OauthClientDetailsController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    /**
     * 根据 ID 查询 OauthClientDetails 数据
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public Result<OauthClientDetails> findById(@PathVariable String id){
        // 调用 OauthClientDetailsService 实现根据主键查询 OauthClientDetails
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.findById(id);
        return new Result<OauthClientDetails>(true, StatusCode.OK, "ID 查询成功!", oauthClientDetails);
    }

}
