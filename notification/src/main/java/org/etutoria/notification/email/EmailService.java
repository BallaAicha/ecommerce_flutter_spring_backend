package org.etutoria.notification.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.etutoria.notification.kafka.order.Product;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.etutoria.notification.email.EmailTemplates.ORDER_CONFIRMATION;
import static org.etutoria.notification.email.EmailTemplates.PAYMENT_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    //cette classe est responsable de l'envoi des emails de confirmation de paiement et de commande

    private final JavaMailSender mailSender;//c'est l'objet qui va nous permettre d'envoyer les emails
    private final SpringTemplateEngine templateEngine;//c'est l'objet qui va nous permettre de lire les fichiers html qui contiennent les templates d'email , il vient de la dépendance thymeleaf

    @Async
    public void sendPaymentSuccessEmail(//cette méthode est responsable de l'envoi de l'email de confirmation de paiement
            String destinationEmail,//l'email de destination qui viendra du message kafka
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();//c'est l'objet qui va nous permettre de construire l'email
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());//c'est l'objet qui va nous permettre de construire l'email
        messageHelper.setFrom("usmanembacke@gmail.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();//le nom du fichier html qui contient le template de l'email

        Map<String, Object> variables = new HashMap<>();//les variables qui seront injectées dans le template
        variables.put("customerName", customerName);//le nom du client
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();// le contexte qui va contenir les variables  qui va être injectées dans le template
        context.setVariables(variables);//injecter les variables dans le context  afin de les rendre accessible dans le template
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());//le sujet de l'email

        try {
            //injecter les variables dans le template pour commencer à construire l'email
            String htmlTemplate = templateEngine.process(templateName, context);//c'est la méthode qui va nous permettre de lire le fichier html qui contient le template et d'injecter les variables dans le template
            messageHelper.setText(htmlTemplate, true);//injecter le template dans le message

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }

    }

    @Async
    public void sendOrderConfirmationEmail(//cette méthode est responsable de l'envoi de l'email de confirmation de commande
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();//c'est l'objet qui va nous permettre de construire l'email
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("usmanembacke@gmail.com");

        final String templateName = ORDER_CONFIRMATION.getTemplate();//le nom du fichier html qui contient le template de l'email

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }

    }
}
