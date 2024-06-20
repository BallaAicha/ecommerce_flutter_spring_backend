package org.etutoria.customer.mappers;

import org.etutoria.customer.customers.Customer;
import org.etutoria.customer.dto.CustomerRequest;
import org.etutoria.customer.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component //permet de dire à Spring de gérer l'instanciation de cette classe cad de créer une instance de cette classe et de la mettre à disposition des autres classes
public class CustomerMapper {

  public Customer toCustomer(CustomerRequest request) {//convertir une requête en un objet Customer
    if (request == null) {
      return null;
    }
    //builder est un patron de conception qui permet de créer des objets complexes en utilisant des objets simples et une étape par étape approche
    return Customer.builder()
        .id(request.id())
        .firstname(request.firstname())
        .lastname(request.lastname())
        .email(request.email())
        .address(request.address())
            .build();
  }

  public CustomerResponse fromCustomer(Customer customer) {//convertir un objet Customer en une réponse
    if (customer == null) {
      return null;
    }
    return new CustomerResponse(
        customer.getId(),
        customer.getFirstname(),
        customer.getLastname(),
        customer.getEmail(),
        customer.getAddress()
    );
  }
}
