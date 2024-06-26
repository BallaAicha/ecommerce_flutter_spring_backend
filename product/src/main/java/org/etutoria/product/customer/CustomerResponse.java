package org.etutoria.product.customer;

public record CustomerResponse(
    String id,
    String firstname,
    String lastname,
    String email
) {

}
