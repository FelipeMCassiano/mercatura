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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @CacheEvict(value = {"products", "productsById"}, allEntries = true)
    public void save(ProductDTO dto) {
        if (productRepository.existsByName(dto.name())) {
            throw new EntityConflictException("Product already exists");
        }

        Product newProduct = new Product(dto);
        newProduct.setPrice(dto.price() * 100);
        productRepository.save(newProduct);
    }

    @Transactional
    @CacheEvict(value = {"products", "productsById"}, allEntries = true)
    public void update(ProductDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setPrice(dto.price());
        product.setName(dto.name());
        product.setStock(dto.stock());
        product.setCategory(dto.category());
        productRepository.save(product);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCategory());
    }

    @Cacheable(value = "products", key = "'page:' + #page + ':size:' + #size")
    public ApiResponseDTO<ProductResponseDTO> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(null, pageable);
        List<ProductResponseDTO> productResponseDTOs = productPage.getContent()
                .stream()
                .map(p -> new ProductResponseDTO(p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock(),
                        p.getCategory())).toList();


        return new ApiResponseDTO<>(productResponseDTOs, productPage.getTotalElements(), productPage.getTotalPages());
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
