package org.etutoria.product.services;

import jakarta.persistence.EntityNotFoundException;
import org.etutoria.product.dto.*;
import org.etutoria.product.entities.Category;
import org.etutoria.product.entities.Image;
import org.etutoria.product.entities.Product;
import org.etutoria.product.mappers.CategoryMapper;
import org.etutoria.product.mappers.ProductMapper;
import org.etutoria.product.repositories.CategoryRepository;
import org.etutoria.product.repositories.ImageRepository;
import org.etutoria.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository mockRepository;
    @Mock
    private ProductMapper mockMapper;
    @Mock
    private CategoryRepository mockCategoryRepository;
    @Mock
    private CategoryMapper mockCategoryMapper;
    @Mock
    private ImageRepository mockImageRepository;
    @Mock
    private ImageService mockImageService;

    private ProductService productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productServiceUnderTest = new ProductService(mockRepository, mockMapper, mockCategoryRepository,
                mockCategoryMapper, mockImageRepository, mockImageService);
    }

    @Test
    void testCreateProduct() {
        // Setup
        final ProductRequest request = new ProductRequest(0, "name", "description", 0.0, new BigDecimal("0.00"), 0, 0L);

        // Configure ProductMapper.toProduct(...).
        final Product product = Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build();
        when(mockMapper.toProduct(
                new ProductRequest(0, "name", "description", 0.0, new BigDecimal("0.00"), 0, 0L))).thenReturn(product);

        // Configure ProductRepository.save(...).
        final Product product1 = Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build();
        when(mockRepository.save(any(Product.class))).thenReturn(product1);

        // Run the test
        final Integer result = productServiceUnderTest.createProduct(request);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testFindById() {
        // Setup
        final ProductResponse expectedResult = new ProductResponse(0, "name", "description", 0.0,
                new BigDecimal("0.00"), 0, "categoryName", "categoryDescription");

        // Configure ProductRepository.findById(...).
        final Optional<Product> product = Optional.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findById(0)).thenReturn(product);

        // Configure ProductMapper.toProductResponse(...).
        final ProductResponse productResponse = new ProductResponse(0, "name", "description", 0.0,
                new BigDecimal("0.00"), 0, "categoryName", "categoryDescription");
        when(mockMapper.toProductResponse(any(Product.class))).thenReturn(productResponse);

        // Run the test
        final ProductResponse result = productServiceUnderTest.findById(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindById_ProductRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> productServiceUnderTest.findById(0)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testFindAll() {
        // Setup
        final List<ProductResponse> expectedResult = List.of(
                new ProductResponse(0, "name", "description", 0.0, new BigDecimal("0.00"), 0, "categoryName",
                        "categoryDescription"));

        // Configure ProductRepository.findAll(...).
        final List<Product> products = List.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findAll()).thenReturn(products);

        // Configure ProductMapper.toProductResponse(...).
        final ProductResponse productResponse = new ProductResponse(0, "name", "description", 0.0,
                new BigDecimal("0.00"), 0, "categoryName", "categoryDescription");
        when(mockMapper.toProductResponse(any(Product.class))).thenReturn(productResponse);

        // Run the test
        final List<ProductResponse> result = productServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindAll_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductResponse> result = productServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testPurchaseProducts() {
        // Setup
        final List<ProductPurchaseRequest> request = List.of(new ProductPurchaseRequest(0, 0.0));
        final List<ProductPurchaseResponse> expectedResult = List.of(
                new ProductPurchaseResponse(0, "name", "description", new BigDecimal("0.00"), 0.0));

        // Configure ProductRepository.findAllByIdInOrderById(...).
        final List<Product> products = List.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findAllByIdInOrderById(List.of(0))).thenReturn(products);

        // Configure ProductMapper.toproductPurchaseResponse(...).
        final ProductPurchaseResponse productPurchaseResponse = new ProductPurchaseResponse(0, "name", "description",
                new BigDecimal("0.00"), 0.0);
        when(mockMapper.toproductPurchaseResponse(any(Product.class), eq(0.0))).thenReturn(productPurchaseResponse);

        // Run the test
        final List<ProductPurchaseResponse> result = productServiceUnderTest.purchaseProducts(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRepository).save(any(Product.class));
    }

    @Test
    void testPurchaseProducts_ProductRepositoryFindAllByIdInOrderByIdReturnsNoItems() {
        // Setup
        final List<ProductPurchaseRequest> request = List.of(new ProductPurchaseRequest(0, 0.0));
        when(mockRepository.findAllByIdInOrderById(List.of(0))).thenReturn(Collections.emptyList());

        // Configure ProductMapper.toproductPurchaseResponse(...).
        final ProductPurchaseResponse productPurchaseResponse = new ProductPurchaseResponse(0, "name", "description",
                new BigDecimal("0.00"), 0.0);
        when(mockMapper.toproductPurchaseResponse(any(Product.class), eq(0.0))).thenReturn(productPurchaseResponse);

        // Run the test
        final List<ProductPurchaseResponse> result = productServiceUnderTest.purchaseProducts(request);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
        verify(mockRepository).save(any(Product.class));
    }

    @Test
    void testFindAllCategories() {
        // Setup
        final List<CategoryResponse> expectedResult = List.of(new CategoryResponse(0, "name", "description"));
        when(mockCategoryRepository.findAll()).thenReturn(List.of(Category.builder().build()));

        // Configure CategoryMapper.toCategory(...).
        final CategoryResponse categoryResponse = new CategoryResponse(0, "name", "description");
        when(mockCategoryMapper.toCategory(any(Category.class))).thenReturn(categoryResponse);

        // Run the test
        final List<CategoryResponse> result = productServiceUnderTest.findAllCategories();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindAllCategories_CategoryRepositoryReturnsNoItems() {
        // Setup
        when(mockCategoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<CategoryResponse> result = productServiceUnderTest.findAllCategories();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindLatestProductsWithImages() throws Exception {
        // Setup
        final List<ProductWithImageResponse> expectedResult = List.of(
                new ProductWithImageResponse(0, "name", "description", "image"));

        // Configure ProductRepository.findTop3ByOrderByCreatedAtDesc(...).
        final List<Product> products = List.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findTop3ByOrderByCreatedAtDesc()).thenReturn(products);

        // Configure ImageService.getImageDetails(...).
        final Image image = Image.builder()
                .idImage(0L)
                .name("name")
                .type("type")
                .image("content".getBytes())
                .build();
        when(mockImageService.getImageDetails(0L)).thenReturn(image);

        // Run the test
        final List<ProductWithImageResponse> result = productServiceUnderTest.findLatestProductsWithImages();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindLatestProductsWithImages_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findTop3ByOrderByCreatedAtDesc()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductWithImageResponse> result = productServiceUnderTest.findLatestProductsWithImages();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindLatestProductsWithImages_ImageServiceThrowsIOException() throws Exception {
        // Setup
        // Configure ProductRepository.findTop3ByOrderByCreatedAtDesc(...).
        final List<Product> products = List.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findTop3ByOrderByCreatedAtDesc()).thenReturn(products);

        when(mockImageService.getImageDetails(0L)).thenThrow(IOException.class);

        // Run the test
        assertThatThrownBy(() -> productServiceUnderTest.findLatestProductsWithImages())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testGetFirstProductPerCategory() {
        // Setup
        final BannerResponse bannerResponse = new BannerResponse();
        bannerResponse.setId(0);
        bannerResponse.setName("name");
        bannerResponse.setDescription("description");
        bannerResponse.setImage("image");
        bannerResponse.setAvailableQuantity(0);
        final List<BannerResponse> expectedResult = List.of(bannerResponse);

        // Configure ProductRepository.findFirstProductPerCategory(...).
        final List<Product> products = List.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findFirstProductPerCategory()).thenReturn(products);

        // Run the test
        final List<BannerResponse> result = productServiceUnderTest.getFirstProductPerCategory();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetFirstProductPerCategory_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findFirstProductPerCategory()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BannerResponse> result = productServiceUnderTest.getFirstProductPerCategory();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindByName() {
        // Setup
        final List<ProductResponse> expectedResult = List.of(
                new ProductResponse(0, "name", "description", 0.0, new BigDecimal("0.00"), 0, "categoryName",
                        "categoryDescription"));

        // Configure ProductRepository.findByNameContaining(...).
        final Collection<Product> products = List.of(Product.builder()
                .id(0)
                .availableQuantity(0.0)
                .images(List.of(Image.builder()
                        .idImage(0L)
                        .name("name")
                        .type("type")
                        .image("content".getBytes())
                        .build()))
                .build());
        when(mockRepository.findByNameContaining("name")).thenReturn(products);

        // Configure ProductMapper.toProductResponse(...).
        final ProductResponse productResponse = new ProductResponse(0, "name", "description", 0.0,
                new BigDecimal("0.00"), 0, "categoryName", "categoryDescription");
        when(mockMapper.toProductResponse(any(Product.class))).thenReturn(productResponse);

        // Run the test
        final List<ProductResponse> result = productServiceUnderTest.findByName("name");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindByName_ProductRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findByNameContaining("name")).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductResponse> result = productServiceUnderTest.findByName("name");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
