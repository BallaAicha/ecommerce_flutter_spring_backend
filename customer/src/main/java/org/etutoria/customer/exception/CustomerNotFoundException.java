package org.etutoria.customer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)//pour dire qu'on a besoin d'appeler le super classe de CustomerNotFoundException pour afficher le message d'erreur
@Data
public class CustomerNotFoundException extends RuntimeException {
  private final String msg;
}
