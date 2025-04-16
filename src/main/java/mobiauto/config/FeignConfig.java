package mobiauto.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "mobiauto.lojista.service")
public class FeignConfig {

}
