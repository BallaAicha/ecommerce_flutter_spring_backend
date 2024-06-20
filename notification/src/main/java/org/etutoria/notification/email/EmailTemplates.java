package org.etutoria.notification.email;

import lombok.Getter;

public enum EmailTemplates {
    //cette classe est une énumération qui contient les différents templates d'email cad les différents types d'email que l'application peut envoyer

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation")//le template d'email de confirmation de commande qui pointe vers le fichier order-confirmation.html
    ;

    @Getter
    private final String template;//le nom du fichier html qui contient le template
    @Getter
    private final String subject;//le sujet de l'email


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
