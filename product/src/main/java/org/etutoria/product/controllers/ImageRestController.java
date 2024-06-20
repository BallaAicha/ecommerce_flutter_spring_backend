package org.etutoria.product.controllers;

import org.etutoria.product.dto.ProductRequest;
import org.etutoria.product.dto.ProductResponse;
import org.etutoria.product.entities.Image;
import org.etutoria.product.entities.Product;
import org.etutoria.product.services.ImageService;
import org.etutoria.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageRestController {
	@Autowired
	ImageService imageService;

	@Autowired
	ProductService productService;



//	@GetMapping(value = "/loadfromFS/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
//	public byte[] getImageFS(@PathVariable("id") Integer id) throws IOException {
//		ProductResponse productResponse = productService.findById(id);
//		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/images/" + productResponse.imagePath()));
//	}

	@PostMapping(value = "/upload")
	public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
		return imageService.uploadImage(file);
	}

	@PostMapping(value = "/uploadImageProd/{idProd}")
	public Image uploadMultiImages(@RequestParam("image") MultipartFile file, @PathVariable("idProd") Integer idProd) throws IOException {
		return imageService.uploadImageProd(file, idProd);
	}

	@GetMapping(value = "/getImagesProd/{idProd}")
	public List<Image> getImagesProd(@PathVariable("idProd") Integer idProd) throws IOException {
		return imageService.getImagesByProd(idProd);
	}

	@GetMapping(value = "/get/info/{id}")
	public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
		return imageService.getImageDetails(id);
	}

	@GetMapping(value = "/load/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
		return imageService.getImage(id);
	}

	@DeleteMapping(value = "/delete/{id}")
	public void deleteImage(@PathVariable("id") Long id) {
		imageService.deleteImage(id);
	}

	@PutMapping(value = "/update")
	public Image updateImage(@RequestParam("image") MultipartFile file) throws IOException {
		return imageService.uploadImage(file);
	}
}