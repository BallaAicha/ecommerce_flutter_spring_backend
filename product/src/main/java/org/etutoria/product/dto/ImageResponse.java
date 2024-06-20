package org.etutoria.product.dto;

public record ImageResponse(
        Long idImage,
        String name,
        String type,
        byte[] image,
        Integer productId
) {
}