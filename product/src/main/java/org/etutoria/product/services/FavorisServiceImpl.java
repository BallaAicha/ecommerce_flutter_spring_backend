package org.etutoria.product.services;
import jakarta.persistence.EntityNotFoundException;
import org.etutoria.product.customer.CustomerClient;
import org.etutoria.product.customer.CustomerResponse;
import org.etutoria.product.dto.FavorisRequest;
import org.etutoria.product.dto.FavorisResponse;
import org.etutoria.product.entities.Favoris;
import org.etutoria.product.entities.Product;
import org.etutoria.product.mappers.FavorisMapper;
import org.etutoria.product.mappers.ProductMapper;
import org.etutoria.product.repositories.FavorisRepository;
import org.etutoria.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Transactional
public class FavorisServiceImpl implements FavorisService{
    private final  FavorisMapper favorisMapper;
    private final FavorisRepository favorisRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CustomerClient customerClient;
    public FavorisServiceImpl(FavorisMapper favorisMapper, FavorisRepository favorisRepository, ProductMapper productMapper, ProductRepository productRepository, CustomerClient customerClient) {
        this.favorisMapper = favorisMapper;
        this.favorisRepository = favorisRepository;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.customerClient = customerClient;
    }
    @Override
    public FavorisResponse getFavoris(Long id) {
        Favoris favoris = favorisRepository.findById(id).orElseThrow(() -> new RuntimeException("Favoris not found"));
        return favorisMapper.toFavoris(favoris);
    }
    @Override
    public FavorisResponse addFavoris(FavorisRequest favorisRequest) {
        // Validate the customer
        String customerId = favorisRequest.customerId();
        Optional<CustomerResponse> customerResponse = customerClient.findCustomerById(customerId);
        if (customerResponse.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with id " + customerId);
        }

        List<Product> products = favorisRequest.productList().stream()
                .map(productRequest -> {
                    // Assuming productRequest contains the product ID
                    Integer productId = productRequest.id();
                    return productRepository.findById(productId)
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));
                })
                .collect(Collectors.toList());

        Favoris favoris = Favoris.builder()
                .productList(products)
                .customerId(customerId) // Set the customerId
                .build();

        favoris = favorisRepository.save(favoris);
        return favorisMapper.toFavoris(favoris);
    }
    @Override
    public FavorisResponse updateFavoris(Long id, FavorisRequest favorisRequest) {
        Favoris favoris = favorisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favoris not found"));
        List<Product> products = favorisRequest.productList().stream()
                .map(productMapper::toProduct)
                .collect(Collectors.toList());
        favoris.setProductList(products);
        favoris = favorisRepository.save(favoris);
        return favorisMapper.toFavoris(favoris);
    }
    @Override
    public void deleteFavoris(Long id) {
        favorisRepository.deleteById(id);

    }

    @Override
    public List<FavorisResponse> getAllFavoris() {
        return favorisRepository.findAll().stream()
                .map(favorisMapper::toFavoris)
                .collect(Collectors.toList());
    }


    //trouver les favoris d'un client
    @Override
    public List<FavorisResponse> getFavorisByCustomerId(String customerId) {
        // Récupérer les favoris du client
        List<Favoris> favoris = favorisRepository.findByCustomerId(customerId);

        // Mapper les entités Favoris en FavorisResponse
        return favoris.stream()
                .map(favorisMapper::toFavoris)
                .collect(Collectors.toList());
    }
}
