package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.dtos.ApiResponseDTO;
import com.felipemcassiano.Mercatura.dtos.ProductDTO;
import com.felipemcassiano.Mercatura.dtos.ProductFilterDTO;
import com.felipemcassiano.Mercatura.dtos.ProductResponseDTO;
import com.felipemcassiano.Mercatura.infra.exceptions.EntityConflictException;
import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.repositories.ProductRepository;
import com.felipemcassiano.Mercatura.repositories.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ValueOperations<String, ProductResponseDTO> redisTemplateValueOps;
    @Value("${api.cache.duration}")
    private long cacheDuration;

    public ProductService(ProductRepository productRepository, RedisTemplate<String, ProductResponseDTO> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplateValueOps = redisTemplate.opsForValue();
    }

    @Transactional
    public void save(ProductDTO dto) {
        if (productRepository.existsByName(dto.name())) {
            throw new EntityConflictException("Product already exists");
        }

        Product newProduct = new Product(dto);
        newProduct.setPrice(dto.price() * 100);
        productRepository.save(newProduct);
    }

    @Transactional
    public void update(ProductDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setPrice(dto.price());
        product.setName(dto.name());
        product.setStock(dto.stock());
        product.setCategory(dto.category());
        productRepository.save(product);
    }

    public ProductResponseDTO findById(Long id) {
        String key = String.format("product:%s", id);
        ProductResponseDTO cachedProduct = redisTemplateValueOps.get(key);
        if (cachedProduct != null) {
            return cachedProduct;
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductResponseDTO productResponse = new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCategory());

        redisTemplateValueOps.set(key, productResponse, Duration.ofMinutes(cacheDuration));

        return productResponse;
    }

    public ApiResponseDTO<ProductResponseDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(null, pageable);

        return new ApiResponseDTO<>(productPage.getContent()
                .stream()
                .map(p -> new ProductResponseDTO(p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock(),
                        p.getCategory())).toList(), productPage.getTotalElements(), productPage.getTotalPages());
    }

    public ApiResponseDTO<ProductResponseDTO> filter(ProductFilterDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(ProductSpecification.filter(dto.priceRange() != null ? dto.priceRange().min() : null,
                dto.priceRange() != null ? dto.priceRange().max() : null,
                dto.stock(),
                dto.category()), pageable);

        return new ApiResponseDTO<>(productPage.getContent().stream().map(x -> new ProductResponseDTO(x.getId(),
                x.getName(),
                x.getPrice(),
                x.getStock(),
                x.getCategory())).toList(),
                productPage.getTotalElements(),
                productPage.getTotalPages());
    }


}
