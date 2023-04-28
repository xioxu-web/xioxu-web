package io.microservice.oauth2.cloud.admin.biz.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.microservice.oauth2.cloud.admin.api.entity.SysOauthClientDetails;
import io.microservice.oauth2.cloud.admin.biz.service.SysOauthClientDetailsService;
import io.microservice.oauth2.cloud.common.security.annotation.Inner;
import io.microservice.oauth2.common.core.util.ResultMsg;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaoxu123
 * 客户端管理模块
 */
@RequestMapping("/client")
@RestController
@RequiredArgsConstructor
@Tag(name = "客户端管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

    private final SysOauthClientDetailsService sysOauthClientDetailsService;

    /**
     *通过ID查询客户端信息
     * @param clientId 客户端id
     * @returnn SysOauthClientDetails
     */
    @GetMapping("/{clientId}")
    public ResultMsg<List<SysOauthClientDetails>> getByClientId(@PathVariable String clientId){
       return ResultMsg.ok(
               sysOauthClientDetailsService.list(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId,clientId)));
    }


    @Inner(value = false)
    @GetMapping("/getClientDetailsById/{clientId}")
    public ResultMsg getClientDetailsById(@PathVariable String clientId){
      return ResultMsg.ok(
              sysOauthClientDetailsService.getOne(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId,clientId),false)
      );
    }

}
