package org.etutoria.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TokenService {

    @Value("${application.config.keycloak-token-url}")//récupère la valeur de la propriété keycloak-token-url
    private String tokenUrl;

    private String username = "ousmane";//username de l'utilisateur
    private String password = "1234";

    public String getToken() {//méthode pour récupérer le token
        RestTemplate restTemplate = new RestTemplate();//crée une instance de RestTemplate qui permet d'envoyer des requêtes HTTP
        HttpHeaders headers = new HttpHeaders();//crée une instance de HttpHeaders qui permet de définir les en-têtes de la requête
        headers.add("Content-Type", "application/x-www-form-urlencoded");//ajoute l'en-tête Content-Type à la requête
        //crée une instance de MultiValueMap qui permet de définir les paramètres de la requête ,
        // multi-value map est une interface qui hérite de l'interface Map
        // il permet de stocker plusieurs valeurs pour une clé donnée
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", "password");//ajoute le paramètre grant_type à la requête
        map.add("client_id", "ecommerce");
        map.add("username", this.username);
        map.add("password", this.password);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);//crée une instance de HttpEntity qui permet de définir le corps et les en-têtes de la requête
        //envoie une requête HTTP POST à l'URL spécifiée avec l'entité et les en-têtes spécifiés
        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);

        return (String) response.getBody().get("access_token");//récupère le token d'accès de la réponse
    }
}