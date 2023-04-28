package io.microservices.shop.service.user.api.fegin;
import io.microservices.shop.common.resp.Result;
import io.microservices.shop.service.user.api.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaoxu123
 */
@FeignClient(name ="shop-service-user")
@RequestMapping(value = "/user")
public interface UserFeign {

    /**
     * 根据 ID 查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping({"/load/{id}"})
    Result<User> findById(@PathVariable("id") String id);

}
