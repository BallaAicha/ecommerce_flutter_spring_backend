package org.etutoria.customer.handler;


import org.etutoria.customer.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
//cette classe permet de gérer les exceptions qui peuvent être levées par les contrôleurs

@RestControllerAdvice //il permet de dire à Spring que cette classe est un gestionnaire d'exceptions
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<String> handle(CustomerNotFoundException exp) {//il permet de gérer les exceptions de type CustomerNotFoundException
    return ResponseEntity //il permet de renvoyer une réponse à l'utilisateur
        .status(HttpStatus.NOT_FOUND)//il permet de dire à Spring de renvoyer une réponse avec un code 404
        .body(exp.getMsg());//il permet de renvoyer un message d'erreur
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)// cette méthode permet de gérer les exceptions de type MethodArgumentNotValidException cad les exceptions qui sont levées lorsqu'une requête n'est pas valide
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
    var errors = new HashMap<String, String>();//il permet de stocker les erreurs de la requête
    exp.getBindingResult().getAllErrors()//il permet de récupérer toutes les erreurs de la requête
            .forEach(error -> {//il permet de parcourir les erreurs de la requête
              var fieldName = ((FieldError) error).getField();//il permet de récupérer le nom du champ qui a une erreur
              var errorMessage = error.getDefaultMessage();//il permet de récupérer le message d'erreur
              errors.put(fieldName, errorMessage);//il permet de stocker le nom du champ et le message d'erreur
            });

    return ResponseEntity
            .status(BAD_REQUEST)
            .body(new ErrorResponse(errors));
  }
}
