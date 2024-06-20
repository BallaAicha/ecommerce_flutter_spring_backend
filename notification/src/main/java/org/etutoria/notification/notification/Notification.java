package org.etutoria.notification.notification;

import lombok.*;
import org.etutoria.notification.kafka.order.OrderConfirmation;
import org.etutoria.notification.kafka.payment.PaymentConfirmation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;//la date de la notification
    private OrderConfirmation orderConfirmation;//les infos de la commande que je voudrais consommer pour notifier le client
    private PaymentConfirmation paymentConfirmation;
}
