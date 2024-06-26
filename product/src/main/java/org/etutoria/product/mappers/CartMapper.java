package org.etutoria.product.mappers;

import org.etutoria.product.dto.CartRequest;
import org.etutoria.product.dto.CartResponse;
import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.entities.Cart;
import org.etutoria.product.entities.Product;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CartMapper {

    public CartResponse toCart(Cart cart){
     return new CartResponse(
             cart.getId(),
             cart.getProductList().stream().map(this::toProductResponse).collect(Collectors.toList()),
             cart.getCustomerId()
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
