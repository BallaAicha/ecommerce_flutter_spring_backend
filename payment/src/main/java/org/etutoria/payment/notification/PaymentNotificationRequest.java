package org.etutoria.payment.notification;



import org.etutoria.payment.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(//represente une requete de notification de paiement avec les données que je veux envoyer
        //
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
