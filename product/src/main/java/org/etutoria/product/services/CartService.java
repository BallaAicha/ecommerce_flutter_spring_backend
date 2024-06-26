package org.etutoria.product.services;

import org.etutoria.product.dto.CartRequest;
import org.etutoria.product.dto.CartResponse;
import org.etutoria.product.dto.FavorisRequest;
import org.etutoria.product.dto.FavorisResponse;

import java.util.List;

public interface CartService {

    CartResponse addCart(CartRequest cartRequest);
    void deleteCart(Long id);
    List<CartResponse> getAllCarts();
    List<CartResponse> getCartByCustomerId(String customerId);
}
