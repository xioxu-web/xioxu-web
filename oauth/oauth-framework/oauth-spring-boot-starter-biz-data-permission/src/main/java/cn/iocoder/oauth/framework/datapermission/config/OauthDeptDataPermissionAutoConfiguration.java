package cn.iocoder.oauth.framework.datapermission.config;

import cn.iocoder.oauth.framework.datapermission.core.rule.dept.DeptDataPermissionRule;
import cn.iocoder.oauth.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.iocoder.oauth.framework.security.core.LoginUser;
import cn.iocoder.oauth.module.system.api.permission.PermissionApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * @author xiaoxu123
 */
@Configuration
@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = {PermissionApi.class, DeptDataPermissionRuleCustomizer.class})
public class OauthDeptDataPermissionAutoConfiguration {

    @Bean
    public
    DeptDataPermissionRule deptDataPermissionRule(PermissionApi permissionApi,
                                                  List<DeptDataPermissionRuleCustomizer> customizers) {
        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionApi);
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }
}
