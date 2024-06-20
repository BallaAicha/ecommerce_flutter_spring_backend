package org.etutoria.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    //cette classe permet de créer un bean de type RestTemplate cad un objet de type RestTemplate qui va permettre d'appeler des services

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
