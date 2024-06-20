package org.etutoria.order.order;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.etutoria.order.product.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)//pour dire que les champs vides ne seront pas affichés
public record OrderRequest(
    Integer id,
    String reference,
    @Positive(message = "Order amount should be positive")
    BigDecimal amount,
    @NotNull(message = "Payment method should be precised")
    PaymentMethod paymentMethod,
    @NotNull(message = "Customer should be present")
    @NotEmpty(message = "Customer should be present")//
    @NotBlank(message = "Customer should be present")//pour dire que le client ne doit pas être vide
    String customerId,//id du client qui a passé la commande
    @NotEmpty(message = "You should at least purchase one product")
    List<PurchaseRequest> products //il contiendra la liste des produits à commander
) {

}
