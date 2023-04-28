package cn.iocoder.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 */
@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = {"${oauth.info.base-package}.server", "${oauth.info.base-package}.module"})
public class OauthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run( OauthServerApplication.class, args);

    }
}
