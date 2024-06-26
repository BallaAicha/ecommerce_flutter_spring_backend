package org.etutoria.product.repositories;

import org.etutoria.product.entities.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavorisRepository extends JpaRepository<Favoris,Long> {
    List<Favoris> findByCustomerId(String customerId);
}
