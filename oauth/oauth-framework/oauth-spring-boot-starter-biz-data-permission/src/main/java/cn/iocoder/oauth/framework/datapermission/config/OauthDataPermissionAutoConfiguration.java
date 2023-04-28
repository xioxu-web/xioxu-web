package cn.iocoder.oauth.framework.datapermission.config;

import cn.iocoder.oauth.framework.datapermission.core.aop.DataPermissionAnnotationAdvisor;
import cn.iocoder.oauth.framework.datapermission.core.db.DataPermissionDatabaseInterceptor;
import cn.iocoder.oauth.framework.datapermission.core.rule.DataPermissionRule;
import cn.iocoder.oauth.framework.datapermission.core.rule.DataPermissionRuleFactory;
import cn.iocoder.oauth.framework.datapermission.core.rule.DataPermissionRuleFactoryImpl;
import cn.iocoder.oauth.framework.mybatis.core.util.MyBatisUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author xiaoxu123
 */
@Configuration
public class OauthDataPermissionAutoConfiguration {
    @Bean
    public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
        return new DataPermissionRuleFactoryImpl(rules);
    }

    @Bean
    public
    DataPermissionDatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor,
                                                                        List<DataPermissionRule> rules) {
        // 创建 DataPermissionDatabaseInterceptor 拦截器
        DataPermissionRuleFactory ruleFactory = dataPermissionRuleFactory(rules);
        DataPermissionDatabaseInterceptor inner = new DataPermissionDatabaseInterceptor(ruleFactory);
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }
}
