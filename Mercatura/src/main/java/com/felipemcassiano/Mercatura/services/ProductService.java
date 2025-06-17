package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.infra.exceptions.EntityConflictException;
import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.models.product.ProductDTO;
import com.felipemcassiano.Mercatura.models.product.ProductFilterDTO;
import com.felipemcassiano.Mercatura.models.product.ProductResponseDTO;
import com.felipemcassiano.Mercatura.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(ProductDTO dto) {
        if (productRepository.existsByName(dto.name())) {
            throw new EntityConflictException("Product already exists");
        }
        Product newProduct = new Product(dto);
        productRepository.save(newProduct);
    }

    @Transactional
    public void update(ProductDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setPrice(dto.price());
        product.setName(dto.name());
        product.setQuantity(dto.quantity());
        productRepository.save(product);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }

    public List<ProductResponseDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(p -> new ProductResponseDTO(p.getId(), p.getName(), p.getPrice(), p.getQuantity())).toList();
    }

    public List<ProductResponseDTO> filter(ProductFilterDTO dto) {

    }


}
