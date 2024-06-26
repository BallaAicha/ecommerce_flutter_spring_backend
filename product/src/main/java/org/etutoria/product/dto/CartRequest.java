package org.etutoria.product.dto;

import java.util.List;

public record CartRequest(
        Long id,
        List<ProductRequest> productList,
        String customerId
) {
}
