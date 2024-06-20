package org.etutoria.product.mappers;


import org.etutoria.product.dto.ImageResponse;
import org.etutoria.product.dto.ProductPurchaseResponse;
import org.etutoria.product.dto.ProductRequest;
import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.entities.Category;
import org.etutoria.product.entities.Image;
import org.etutoria.product.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMapper {
    private final ImageMapper imageMapper;

    @Autowired
    public ProductMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }
//    public Product toProduct(ProductRequest request) {
//        Image image = Image.builder()
//                .name(request.imageName())
//                .type(request.imageType())
//                .image(request.imageData())
//                .build();
//
//
//        return Product.builder()
//                .id(request.id())
//                .name(request.name())
//                .description(request.description())
//                .availableQuantity(request.availableQuantity())
//                .price(request.price())
//                .category(
//                        Category.builder()
//                                .id(request.categoryId())
//                                .build()
//                )
//
//                .imagePath(request.imagePath())
//                .images(List.of(image))
//                .build();
//    }

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {

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


    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
