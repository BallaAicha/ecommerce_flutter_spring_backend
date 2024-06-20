package org.etutoria.customer.customers;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated //permet de valider les données de l'objet cad de vérifier que les données sont valides
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;//le code postal
}
