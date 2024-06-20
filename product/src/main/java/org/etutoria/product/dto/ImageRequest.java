package org.etutoria.product.dto;

public record ImageRequest(
        Long idImage,
        String name,
        String type,
        byte[] image,
        Integer productId
) {
}