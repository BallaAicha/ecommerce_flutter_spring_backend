package org.etutoria.order.customer;

import org.etutoria.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
@FeignClient(
        name = "customer-service",//le nom du service à appeler
        url = "${application.config.customer-url}",
        configuration = FeignConfig.class//la configuration du client Feign
)

public interface CustomerClient {

  @GetMapping("/{customer-id}")
  Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId);
}
