package org.etutoria.order.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    //cette classe permet de configurer le client Feign pour qu'il ajoute le token JWT dans le header de chaque requête

    @Autowired
    private TokenService tokenService;//injecte une instance de TokenService

    @Bean//déclare une méthode qui retourne un bean cad un objet qui sera géré par Spring et qui sera injecté dans les classes qui en dépendent
    public RequestInterceptor requestInterceptor() {//méthode qui retourne un objet de type RequestInterceptor
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {//il prend en paramètre un objet de type RequestTemplate cad un objet qui permet de définir les paramètres de la requête
                String token = tokenService.getToken();//récupère le token JWT
                template.header("Authorization", "Bearer " + token);//ajoute le token JWT dans le header de la requête
            }
        };
    }
}

