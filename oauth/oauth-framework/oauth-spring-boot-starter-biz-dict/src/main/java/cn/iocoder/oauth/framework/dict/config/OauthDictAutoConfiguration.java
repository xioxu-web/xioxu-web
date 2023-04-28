package cn.iocoder.oauth.framework.dict.config;
import cn.iocoder.oauth.framework.dict.core.service.DictDataFrameworkService;
import cn.iocoder.oauth.framework.dict.core.util.DictFrameworkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OauthDictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public
    DictFrameworkUtils dictUtils(DictDataFrameworkService service) {
        DictFrameworkUtils.init(service);
        return new DictFrameworkUtils();
    }

}
