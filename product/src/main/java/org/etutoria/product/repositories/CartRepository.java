package org.etutoria.product.repositories;
import org.etutoria.product.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByCustomerId(String customerId);
}
