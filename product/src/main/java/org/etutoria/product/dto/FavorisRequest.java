package org.etutoria.product.dto;

import java.util.List;

public record FavorisRequest(
        Long id,
        List<ProductRequest> productList
) {
}
