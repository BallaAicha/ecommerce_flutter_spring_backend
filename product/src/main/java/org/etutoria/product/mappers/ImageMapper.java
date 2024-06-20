package org.etutoria.product.mappers;

import org.etutoria.product.dto.ImageResponse;
import org.etutoria.product.entities.Image;
import org.springframework.stereotype.Service;

@Service
public class ImageMapper {
    public ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getIdImage(),
                image.getName(),
                image.getType(),
                image.getImage(),
                image.getProduit().getId()
        );
    }
}
