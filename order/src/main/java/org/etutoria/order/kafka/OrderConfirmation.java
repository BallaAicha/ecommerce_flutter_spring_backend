package org.etutoria.order.kafka;


import org.etutoria.order.customer.CustomerResponse;
import org.etutoria.order.order.PaymentMethod;
import org.etutoria.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;
//les infos de la commande que je voudrais envoyer au broker kafka pour notifier le microservice de notification
public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}
