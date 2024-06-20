package org.etutoria.product.services;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.etutoria.product.ProductApplication;
import org.etutoria.product.entities.Image;
import org.etutoria.product.entities.Product;
import org.etutoria.product.repositories.ImageRepository;
import org.etutoria.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public Image uploadImage(MultipartFile file) throws IOException {
		return imageRepository.save(
				Image.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.image(file.getBytes())
				.build());
	}





	@Override
	public Image getImageDetails(Long id) throws IOException {
		Optional<Image> dbImage = imageRepository.findById(id);
		if (dbImage.isPresent()) {
			return Image.builder().idImage(dbImage.get().getIdImage()).name(dbImage.get().getName())
					.type(dbImage.get().getType()).image(dbImage.get().getImage()).build();
		} else {
			throw new IOException("Image with id " + id + " not found");
		}
	}

	@Override
	public ResponseEntity<byte[]> getImage(Long id) {
		Optional<Image> dbImage = imageRepository.findById(id);
		if (dbImage.isPresent()) {
			return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
					.body(dbImage.get().getImage());
		} else {


			throw new RuntimeException("Image with id " + id + " not found");
		}
	}

	@Override
	public void deleteImage(Long id) {
		imageRepository.deleteById(id);
	}



	@Override
	public Image uploadImageProd(MultipartFile file, Integer idProd) throws IOException {
		Optional<Product> product = productRepository.findById(idProd);
		if (product.isPresent()) {
			return imageRepository.save(
					Image.builder()
					.name(file.getOriginalFilename())
					.type(file.getContentType())
					.image(file.getBytes())
					.produit(product.get()).build());
		} else {
			throw new IOException("Product with id " + idProd + " not found");
		}
	}

	@Override
	public List<Image> getImagesByProd(Integer prodId) {
		Optional<Product> product = productRepository.findById(prodId);
		if (product.isPresent()) {
			return product.get().getImages();
		} else {
			throw new RuntimeException("Product with id " + prodId + " not found");
		}
	}


	@Override
	public List<Image> getImagesParProd(Integer prodId) {
		Product p = productRepository.findById(prodId).get();
		return p.getImages();
	}





}