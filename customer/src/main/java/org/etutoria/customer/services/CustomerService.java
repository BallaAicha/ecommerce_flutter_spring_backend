package org.etutoria.customer.services;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.etutoria.customer.customers.Customer;
import org.etutoria.customer.dto.CustomerRequest;
import org.etutoria.customer.dto.CustomerResponse;
import org.etutoria.customer.exception.CustomerNotFoundException;
import org.etutoria.customer.mappers.CustomerMapper;
import org.etutoria.customer.repositories.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

//  public String createCustomer(CustomerRequest request) {
//    var customer = this.repository.save(mapper.toCustomer(request));
//    return customer.getId();//on retourne l'identifiant du client
//  }

  public ResponseEntity<Map<String, String>> createCustomer(CustomerRequest request) {
    var customer = this.repository.save(mapper.toCustomer(request));
    Map<String, String> response = new HashMap<>();
    response.put("id", customer.getId());
    return ResponseEntity.ok(response); // retourne l'identifiant du client dans un objet JSON
  }

  public void updateCustomer(CustomerRequest request) {
    var customer = this.repository.findById(request.id())
        .orElseThrow(() -> new CustomerNotFoundException(
            String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
        ));
    mergeCustomer(customer, request);//on fusionne les informations du client avec les nouvelles informations
    this.repository.save(customer);
  }

  private void mergeCustomer(Customer customer, CustomerRequest request) {
    //cette méthode permet de fusionner les informations du client avec les nouvelles informations cad de mettre à jour les informations du client en cas de modification
    if (StringUtils.isNotBlank(request.firstname())) {//si le prénom est non vide
      customer.setFirstname(request.firstname());//on met à jour le prénom
    }
    if (StringUtils.isNotBlank(request.email())) {//si l'email est non vide
      customer.setEmail(request.email());//on met à jour l'email
    }
    if (request.address() != null) {
      customer.setAddress(request.address());
    }
  }

  public List<CustomerResponse> findAllCustomers() {
    return  this.repository.findAll()
        .stream()
        .map(this.mapper::fromCustomer)
        .collect(Collectors.toList());
  }

  public CustomerResponse findById(String id) {
    return this.repository.findById(id)
        .map(mapper::fromCustomer)
        .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
  }

  public boolean existsById(String id) {
    return this.repository.findById(id)
        .isPresent();
  }

  public void deleteCustomer(String id) {
    this.repository.deleteById(id);
  }
}
