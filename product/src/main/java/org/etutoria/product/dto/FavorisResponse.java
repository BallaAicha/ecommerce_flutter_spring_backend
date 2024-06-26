package org.etutoria.product.dto;

import java.util.List;

public record FavorisResponse(
        Long id,
        List<ProductResponse> productList,
        String customerId
) {
}
