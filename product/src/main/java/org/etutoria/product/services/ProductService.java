package org.etutoria.product.services;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.etutoria.product.dto.*;
import org.etutoria.product.entities.Image;
import org.etutoria.product.entities.Product;
import org.etutoria.product.exception.ProductPurchaseException;
import org.etutoria.product.mappers.CategoryMapper;
import org.etutoria.product.mappers.ProductMapper;
import org.etutoria.product.repositories.CategoryRepository;
import org.etutoria.product.repositories.ImageRepository;
import org.etutoria.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final  CategoryRepository categoryRepository;
    private final  CategoryMapper  categoryMapper;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public Integer createProduct(
            ProductRequest request
    ) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

//    public Integer createProduct(ProductRequest request, Long imageId) {
//        var product = mapper.toProduct(request);
//        if (imageId != null) {
//            Image image = imageRepository.findById(imageId)
//                    .orElseThrow(() -> new EntityNotFoundException("Image not found with ID:: " + imageId));
//            image.setProduit(product);
//            product.setImages(List.of(image));
//        }
//        return repository.save(product).getId();
//    }

    public ProductResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            //cette methode permet d'acheter un produit
            List<ProductPurchaseRequest> request //liste des produits a acheter
    ) {
        //on recupere les id des produits a acheter  et on les stocke dans une liste
        var productIds = request //je veux achter par exmple les produits avec les id 1, 2, 3
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);// on recupere les produits a acheter dans la base de donnees en respectant l'ordre des id
        //et que ici j'ai seulement 2 produits avec les id 1 et 2
        if (productIds.size() != storedProducts.size()) {//si la taille des produits a acheter est differente de la taille des produits recuperes dans la base de donnees
            throw new ProductPurchaseException("One or more products does not exist");//on leve une exception en lui disant qu'un ou plusieurs produits n'existent pas
        }
        //on trie les produits a acheter dans le cas ou ils ne sont pas dans l'ordre
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();//on cree une liste de produits achetes
        for (int i = 0; i < storedProducts.size(); i++) {//on parcourt la liste des produits a acheter et la liste des produits recuperes dans la base de donnees
            var product = storedProducts.get(i);//on recupere le produit a acheter dans la liste des produits recuperes dans la base de donnees
            var productRequest = sortedRequest.get(i);//on recupere le produit a acheter dans la liste des produits a acheter
            if (product.getAvailableQuantity() < productRequest.quantity()) {//si la quantite disponible du produit est inferieure a la quantite a acheter
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            ///le cas ou la quantite disponible du produit est superieure ou egale a la quantite a acheter cad il est suffisant et disponible dans ce cas je peux l'acheter
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();//on calcule la nouvelle quantite disponible du produit
            product.setAvailableQuantity(newAvailableQuantity);//on met a jour la quantite disponible du produit
            repository.save(product);//on sauvegarde le produit dans la base de donnees
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));//on ajoute le produit achete dans la liste des produits achetes
        }
        return purchasedProducts;
    }

    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategory)
                .collect(Collectors.toList());
    }
    public List<ProductWithImageResponse> findLatestProductsWithImages() {
        List<Product> latestProducts = repository.findTop3ByOrderByCreatedAtDesc();
        return latestProducts.stream().map(product -> {
            ImageResponse imageResponse = null;
            try {
                if (!product.getImages().isEmpty()) {
                    Image image = imageService.getImageDetails(product.getImages().get(0).getIdImage());
                    imageResponse = new ImageResponse(
                            image.getIdImage(),
                            image.getName(),
                            image.getType(),
                            image.getImage(),
                            product.getId()
                    );
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to load image", e);
            }
            return new ProductWithImageResponse(product, imageResponse);
        }).collect(Collectors.toList());
    }






    public List<BannerResponse> getFirstProductPerCategory() {
        List<Product> products = repository.findFirstProductPerCategory();
        return products.stream().map(this::mapToBannerResponse).collect(Collectors.toList());
    }

    private BannerResponse mapToBannerResponse(Product product) {
        ImageResponse imageResponse = null;
        if (!product.getImages().isEmpty()) {
            Image image = product.getImages().get(0);
            imageResponse = new ImageResponse(
                    image.getIdImage(),
                    image.getName(),
                    image.getType(),
                    image.getImage(),
                    product.getId()
            );
        }
        return new BannerResponse(product, imageResponse);
    }


    public List<ProductResponse> findByName(String name) {

        return repository.findByNameContaining(name)
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
