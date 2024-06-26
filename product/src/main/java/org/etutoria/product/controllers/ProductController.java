package org.etutoria.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.etutoria.product.dto.*;
import org.etutoria.product.entities.Image;
import org.etutoria.product.services.CartService;
import org.etutoria.product.services.FavorisService;
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

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    @Autowired
    ImageService imageService;
    @Autowired
    private FavorisService favorisService;
    @Autowired
    private CartService cartService;



//    @PostMapping
//    public ResponseEntity<Integer> createProduct(
//            @RequestBody @Valid ProductRequest request
//    ) {
//        return ResponseEntity.ok(service.createProduct(request));
//    }

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request
    ) {
        Integer productId = service.createProduct(request);
        return ResponseEntity.ok(productId);
    }


//    @PostMapping
//    public ResponseEntity<Integer> createProduct(
//            @RequestBody @Valid ProductRequest request,
//            @RequestParam(required = false) Long imageId
//    ) {
//        return ResponseEntity.ok(service.createProduct(request, imageId));
//    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> request
    ) {
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("product-id") Integer productId
    ) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {
        return ResponseEntity.ok(service.findAllCategories());
    }

    @PostMapping(value = "/upload")
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable Long id)  {
        byte[] image = imageService.getImage(id).getBody();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }



    @RequestMapping(value = "/get/info/{id}" , method = RequestMethod.GET)
    public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
        return imageService.getImageDetails(id) ;
    }


    @PostMapping(value = "/uploadImageProd/{idProd}")
    public Image uploadMultiImages(@RequestParam("image") MultipartFile file, @PathVariable("idProd") Integer idProd) throws IOException {
        return imageService.uploadImageProd(file, idProd);
    }

    @RequestMapping(value = "/getImagesProd/{idProd}" , method = RequestMethod.GET)
    public List<Image> getImagesProd(@PathVariable("idProd") Integer idProd)
            throws IOException {
        return imageService.getImagesParProd(idProd);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
    }

    @PutMapping(value = "/update")
    public Image updateImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }


    @GetMapping("/latest")
    public ResponseEntity<List<ProductWithImageResponse>> getLatestProducts() {
        List<ProductWithImageResponse> latestProducts = service.findLatestProductsWithImages();
        return ResponseEntity.ok(latestProducts);
    }



    @GetMapping("/firstProductPerCategory")
    public ResponseEntity<List<BannerResponse>> getFirstProductPerCategory() {
        List<BannerResponse> banners = service.getFirstProductPerCategory();
        return ResponseEntity.ok(banners);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }


    @PostMapping("/favoris")
    public ResponseEntity<FavorisResponse> addFavoris(
            @RequestBody @Valid FavorisRequest favorisRequest) {
        FavorisResponse favorisResponse = favorisService.addFavoris(favorisRequest);
        return ResponseEntity.ok(favorisResponse);
    }

    @GetMapping("/favoris/{customerId}")
    public ResponseEntity<List<FavorisResponse>> getFavorisByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(favorisService.getFavorisByCustomerId(customerId));
    }

    @PostMapping("/carts")
    public ResponseEntity<CartResponse> addCart(
            @RequestBody @Valid CartRequest cartRequest) {
        CartResponse cartResponse = cartService.addCart(cartRequest);
        return ResponseEntity.ok(cartResponse);
    }


    @GetMapping("/carts/{customerId}")
    public ResponseEntity<List<CartResponse>> getCartsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }


}
