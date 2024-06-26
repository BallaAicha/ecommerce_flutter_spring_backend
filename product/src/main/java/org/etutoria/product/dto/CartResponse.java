package org.etutoria.product.dto;

import java.util.List;

public record CartResponse(
        Long id,
        List<ProductResponse> productList,
        String customerId

) {
}
