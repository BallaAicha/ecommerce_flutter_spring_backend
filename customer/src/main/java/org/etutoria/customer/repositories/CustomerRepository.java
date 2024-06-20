package org.etutoria.customer.repositories;

import org.etutoria.customer.customers.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String > {

}
