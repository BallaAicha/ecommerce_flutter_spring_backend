package org.etutoria.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.etutoria.product.entities.Image;
import org.etutoria.product.entities.Product;

import java.util.Arrays;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithImageResponse {
    private Integer id;
    private String name;
    private String description;
    private String image;

    public ProductWithImageResponse(Product product, ImageResponse imageResponse) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.image = imageResponse != null ? "data:" + imageResponse.type() + ";base64," + Base64.getEncoder().encodeToString(imageResponse.image()) : null;
    }
}