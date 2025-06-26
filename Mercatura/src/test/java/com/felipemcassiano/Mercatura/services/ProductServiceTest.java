package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.dtos.ApiResponseDTO;
import com.felipemcassiano.Mercatura.dtos.ProductDTO;
import com.felipemcassiano.Mercatura.dtos.ProductFilterDTO;
import com.felipemcassiano.Mercatura.dtos.ProductResponseDTO;
import com.felipemcassiano.Mercatura.infra.exceptions.EntityConflictException;
import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.models.product.ProductCategory;
import com.felipemcassiano.Mercatura.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("Should create product successfully when everything is OK")
    void saveCase1() {
        ProductDTO productDTO = new ProductDTO("test", 10L, 10L, ProductCategory.HOME);

        when(productRepository.existsByName(productDTO.name())).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> productService.save(productDTO));
    }

    @Test
    @DisplayName("Should fail when product with name already exists")
    void saveCase2() {
        ProductDTO productDTO = new ProductDTO("test", 10L, 10L, ProductCategory.HOME);
        when(productRepository.existsByName(productDTO.name())).thenReturn(true);
        Assertions.assertThrowsExactly(EntityConflictException.class, () -> productService.save(productDTO));
    }

    @Test
    @DisplayName("Should update product successfully when everything is OK")
    void updateCase1() {
        ProductDTO productDTO = new ProductDTO("test", 10L, 10L, ProductCategory.HOME);
        Product product = new Product(productDTO);
        Long productId = 1L;
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Assertions.assertDoesNotThrow(() -> productService.update(productDTO, productId));
    }

    @Test
    @DisplayName("Should fail update product when product not found")
    void updateCase2() {
        ProductDTO productDTO = new ProductDTO("test", 10L, 10L, ProductCategory.HOME);
        Long productId = 1000000L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> productService.update(productDTO, productId));
    }

    @Test
    @DisplayName("Should find product successfully")
    void findByIdCase1() {
        Product product = new Product();
        Long productId = 1L;
        product.setId(productId);
        product.setName("test");
        product.setCategory(ProductCategory.HOME);
        product.setPrice(10L);
        product.setStock(100L);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Assertions.assertDoesNotThrow(() -> productService.findById(1L));
    }

    @Test
    @DisplayName("Should fail find product")
    void findByIdCase2() {
        Long productId = 1000000L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    @DisplayName("Should find all products successfully")
    void findAll() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("test" + i);
            product.setCategory(ProductCategory.HOME);
            product.setPrice(10L + (long) i);
            product.setStock(100L + (long) i);

            products.add(product);
        }
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = new PageImpl<>(products, pageable, size);

        when(productRepository.findAll(null, pageable)).thenReturn(productPage);

        ApiResponseDTO<ProductResponseDTO> responseDTO = productService.findAll(page, size);
        assertEquals(size, responseDTO.items().size());
    }

    @Test
    @DisplayName("Should find products by filter successfully")
    void filter_case1() {
        ProductFilterDTO filterDTO = new ProductFilterDTO(null, null, null); // no filtering
        int oneBasedPage = 0;
        int pageSize = 2;

        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < pageSize; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("Product " + i);
            product.setCategory(ProductCategory.ELECTRONICS);
            product.setPrice(10L + (long) i);
            product.setStock(100L + (long) i);
            productList.add(product);
        }

        Page<Product> page = new PageImpl<>(productList);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        ApiResponseDTO<ProductResponseDTO> response = productService.filter(filterDTO, oneBasedPage, pageSize);

        verify(productRepository).findAll(any(Specification.class), pageableCaptor.capture());
        Pageable actualPageable = pageableCaptor.getValue();

        assertEquals(0, actualPageable.getPageNumber(), "Expected page index 0 for zero-based page=1");
        assertEquals(pageSize, actualPageable.getPageSize());

        assertEquals(2, response.items().size());
        assertEquals("Product 0", response.items().get(0).name());
        assertEquals("Product 1", response.items().get(1).name());
    }


}