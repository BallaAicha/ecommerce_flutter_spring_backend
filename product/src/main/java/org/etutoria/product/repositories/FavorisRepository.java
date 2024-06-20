package org.etutoria.product.repositories;

import org.etutoria.product.entities.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorisRepository extends JpaRepository<Favoris,Long> {
}
