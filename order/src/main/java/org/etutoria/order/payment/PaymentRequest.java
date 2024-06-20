package org.etutoria.order.payment;



import org.etutoria.order.customer.CustomerResponse;
import org.etutoria.order.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,//id de la commande
    String orderReference,//reference de la commande
    CustomerResponse customer//informations sur le client
) {
}
