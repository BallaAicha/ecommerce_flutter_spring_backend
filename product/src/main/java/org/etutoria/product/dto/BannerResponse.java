package org.etutoria.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.etutoria.product.entities.Product;

import java.math.BigDecimal;



import java.math.BigDecimal;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerResponse {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Integer availableQuantity;
    private BigDecimal price;
    private String categoryName;
    private String categoryDescription;

    public BannerResponse(Product product, ImageResponse imageResponse) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.image = imageResponse != null ? "data:" + imageResponse.type() + ";base64," + Base64.getEncoder().encodeToString(imageResponse.image()) : "placeholder-url";
        this.availableQuantity = (int) product.getAvailableQuantity();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName();
        this.categoryDescription = product.getCategory().getDescription();
    }
}
