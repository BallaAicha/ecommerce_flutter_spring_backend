package org.etutoria.order.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        //pour indiquer le nom du service à appeler afin de faire le paiement
        //name = "product-service", signifie que le service à appeler est le service product-service pour faire le paiement car il con
        ///l'url du service payment-service est défini dans le fichier application.properties
    name = "payment-service",
    url = "${application.config.payment-url}"
)
public interface PaymentClient {
  //cette méthode permet de faire une requête POST pour demander le paiement d'une commande

  @PostMapping
  Integer requestOrderPayment(@RequestBody PaymentRequest request);
}
