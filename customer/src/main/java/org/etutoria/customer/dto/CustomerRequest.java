package org.etutoria.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.etutoria.customer.customers.Address;
//record permet de créer une classe avec des propriétés en lecture seule cad une fois que les
// propriétés sont initialisées,
// elles ne peuvent plus être modifiées ,son intérêt est de simplifier la création de classes
// simples qui servent juste à encapsuler des données
public record CustomerRequest(
    String id,
    @NotNull(message = "Customer firstname is required")
    String firstname,
    @NotNull(message = "Customer firstname is required")
    String lastname,
    @NotNull(message = "Customer Email is required")
    @Email(message = "Customer Email is not a valid email address") //permet de dire à Spring de valider que l'email est bien un email
    String email,
    Address address
) {

}
