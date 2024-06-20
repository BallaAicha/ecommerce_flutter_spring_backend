package org.etutoria.product.dto;

import java.math.BigDecimal;
//cette classe est utilisée pour renvoyer les produits achetés
public record ProductPurchaseResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
