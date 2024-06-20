package org.etutoria.product.mappers;

import org.etutoria.product.dto.FavorisResponse;
import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.entities.Favoris;
import org.etutoria.product.entities.Product;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class FavorisMapper {
    public FavorisResponse toFavoris(Favoris favoris) {
        return new FavorisResponse(
                favoris.getId(),
                favoris.getProductList().stream().map(this::toProductResponse).collect(Collectors.toList())
        );
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }
}