package org.etutoria.product.repositories;

import org.etutoria.product.dto.ProductRequest;
import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface
ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByIdInOrderById(List<Integer> ids); //recuperer les produits par id en respectant l'ordre des id cad l'ordre dans lequel ils ont ete inseres

    List<Product> findTop3ByOrderByCreatedAtDesc();

    @Query("SELECT p FROM Product p WHERE p.id IN (SELECT MIN(p2.id) FROM Product p2 GROUP BY p2.category.id)")
    List<Product> findFirstProductPerCategory();


    Collection<Product> findByNameContaining(String name);
    //recommander producs

}
