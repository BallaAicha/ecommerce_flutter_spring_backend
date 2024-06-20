package org.etutoria.product.services;

import java.io.IOException;
import java.util.List;

import org.etutoria.product.entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	Image uploadImage(MultipartFile file) throws IOException;
	Image getImageDetails(Long id) throws IOException;
	ResponseEntity<byte[]> getImage(Long id) ;
	void deleteImage(Long id);
	Image uploadImageProd(MultipartFile file,Integer idProd) throws IOException;
	List<Image> getImagesByProd(Integer prodId);
	List<Image> getImagesParProd(Integer prodId);
}