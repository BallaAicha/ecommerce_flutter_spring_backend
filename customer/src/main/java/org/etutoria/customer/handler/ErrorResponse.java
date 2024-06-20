package org.etutoria.customer.handler;

import java.util.Map;
//cette classe permet de renvoyer des erreurs lorsqu'une requête échoue , elle contient une map qui contient les erreurs de la requête
public record ErrorResponse(
    Map<String, String> errors//il permet de stocker les erreurs de la requête
) {

}
