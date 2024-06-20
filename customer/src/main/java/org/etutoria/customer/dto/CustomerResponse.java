package org.etutoria.customer.dto;

import org.etutoria.customer.customers.Address;

public record CustomerResponse(
    String id,
    String firstname,
    String lastname,
    String email,
    Address address
) {

}
