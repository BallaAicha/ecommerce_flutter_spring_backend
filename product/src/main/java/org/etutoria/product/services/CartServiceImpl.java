package org.etutoria.product.services;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.etutoria.product.customer.CustomerClient;
import org.etutoria.product.customer.CustomerResponse;
import org.etutoria.product.dto.CartRequest;
import org.etutoria.product.dto.CartResponse;
import org.etutoria.product.entities.Cart;
import org.etutoria.product.entities.Product;
import org.etutoria.product.mappers.CartMapper;
import org.etutoria.product.mappers.ProductMapper;
import org.etutoria.product.repositories.CartRepository;
import org.etutoria.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final  CartRepository cartRepository;
    private  final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CustomerClient customerClient;
    @Override
    public CartResponse addCart(CartRequest cartRequest) {
     String customerId = cartRequest.customerId();
        Optional<CustomerResponse> customerResponse = customerClient.findCustomerById(customerId);
        if (customerResponse.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id " + customerId);
        }
        List<Product> products = cartRequest.productList().stream()
                .map(productRequest -> {
                    Integer productId = productRequest.id();
                    return productRepository.findById(productId)
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));
                })
                .collect(Collectors.toList());
        Cart cart = Cart.builder()
                .productList(products)
                .customerId(customerId)
                .build();
        cartRepository.save(cart);
        return cartMapper.toCart(cart);
    }

    @Override
    public void deleteCart(Long id) {

    }

    @Override
    public List<CartResponse> getAllCarts() {
        return cartRepository.findAll().stream().map(cartMapper::toCart).collect(Collectors.toList());
    }
    @Override
    public List<CartResponse> getCartByCustomerId(String customerId) {
       List<Cart> carts = cartRepository.findByCustomerId(customerId);
       return carts.stream().map(cartMapper::toCart).collect(Collectors.toList());
    }


}
