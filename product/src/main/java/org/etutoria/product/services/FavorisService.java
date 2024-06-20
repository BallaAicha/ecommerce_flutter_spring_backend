package org.etutoria.product.services;

import org.etutoria.product.dto.FavorisRequest;
import org.etutoria.product.dto.FavorisResponse;
import org.etutoria.product.entities.Favoris;

import java.util.List;

public interface FavorisService {
    FavorisResponse getFavoris(Long id);
    FavorisResponse addFavoris(FavorisRequest favorisRequest);
    FavorisResponse updateFavoris(Long id, FavorisRequest favorisRequest);
    void deleteFavoris(Long id);
    List<FavorisResponse> getAllFavoris();

}
