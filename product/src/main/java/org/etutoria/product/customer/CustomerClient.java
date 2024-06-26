package org.etutoria.product.customer;


import org.etutoria.product.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
@FeignClient(
        name = "customer-service",//le nom du service à appeler
        url = "${application.config.customer-urls}",
        configuration = FeignConfig.class
)

public interface CustomerClient {

  @GetMapping("/{customer-id}")
  Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") String customerId);
}
