package org.etutoria.order.product;


import lombok.RequiredArgsConstructor;
import org.etutoria.order.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {
    //cette classe permet d'acheter des produits en appelant le service product

    @Value("${application.config.product-url}")//l'url du service à appeler est injectée dans cette variable, le @value permet de récupérer la valeur d'une propriété dans le fichier application.properties
    private String productUrl;//on injecte l'url du service product cad l'url du service qui permet de gérer les produits
    private final RestTemplate restTemplate;//utilisation de RestTemplate pour appeler le service qui est equivalent à l'utilisation de FeignClient

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        //cette methode permet d'acheter des produits
        HttpHeaders headers = new HttpHeaders();//on crée un objet de type HttpHeaders qui va contenir les headers de la requête
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);//on ajoute le header Content-Type avec la valeur application/json
    //prepare la requête à envoyer au service product pour acheter des produits en utilisant RestTemplate
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);//on crée un objet de type HttpEntity qui va contenir le corps de la requête et les headers
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {//on crée un objet de type ParameterizedTypeReference qui va contenir le type de la réponse
        };
        //envoie la requête au service product pour acheter des produits en utilisant RestTemplate
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                //permet d'appeler le service product pour acheter des produits
                productUrl + "/purchase",//l'url du service à appeler
                POST,//la méthode HTTP à utiliser
                requestEntity,//le corps de la requête
                responseType//le type de la réponse
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the products purchase: " + responseEntity.getStatusCode());
        }
        return  responseEntity.getBody();//retourne la réponse
    }

}
