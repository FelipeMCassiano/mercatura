package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.dtos.AddItemDTO;
import com.felipemcassiano.Mercatura.dtos.CartProductDTO;
import com.felipemcassiano.Mercatura.infra.exceptions.NotEnoughStockException;
import com.felipemcassiano.Mercatura.infra.exceptions.NotFoundEntityException;
import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.models.product.ProductCategory;
import com.felipemcassiano.Mercatura.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ShoppingCartServiceTest {

    @Mock
    private ListOperations<String, CartProductDTO> listOps;
    @Mock
    private RedisTemplate<String, CartProductDTO> redisTemplate;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("Should add a new product to cart when everything is OK")
    void addToCartCase1() {
        Long productId = 1L;

        Product product = initProduct(productId);

        AddItemDTO addItemDTO = new AddItemDTO(1L, 1L);

        when(redisTemplate.opsForList()).thenReturn(listOps);
        when(redisTemplate.opsForList().range("", 0, -1)).thenReturn((new ArrayList<>()));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));


        Assertions.assertDoesNotThrow(() -> shoppingCartService.addToCart("test@gmail.com", addItemDTO));
    }

    @Test
    @DisplayName("Should add a product quantity when already is in cart")
    void addToCartCase2() {
        List<CartProductDTO> cartProductDTOList = List.of(
                new CartProductDTO(1L, "Product 1", 1000L, 1L, ProductCategory.HOME),
                new CartProductDTO(2L, "Product 2", 2000L, 1L, ProductCategory.ELECTRONICS)
        );
        Long productId = 1L;
        Product product = initProduct(productId);

        AddItemDTO addItemDTO = new AddItemDTO(1L, 1L);

        when(redisTemplate.opsForList()).thenReturn(listOps);
        when(redisTemplate.opsForList().range(anyString(), anyLong(), anyLong())).thenReturn(cartProductDTOList);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Assertions.assertDoesNotThrow(() -> shoppingCartService.addToCart("test@gmail.com", addItemDTO));
    }

    @Test
    @DisplayName("Should fail when try to add a product that does not have stock")
    void addToCartCase3() {
        List<CartProductDTO> cartProductDTOList = List.of(
                new CartProductDTO(1L, "Product 1", 1000L, 1L, ProductCategory.HOME),
                new CartProductDTO(2L, "Product 2", 2000L, 1L, ProductCategory.ELECTRONICS)
        );
        Long productId = 1L;
        Product product = initProduct(productId);
        product.setStock(1L);

        AddItemDTO addItemDTO = new AddItemDTO(1L, 1L);

        when(redisTemplate.opsForList()).thenReturn(listOps);
        when(redisTemplate.opsForList().range(anyString(), anyLong(), anyLong())).thenReturn(cartProductDTOList);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Assertions.assertThrowsExactly(NotEnoughStockException.class, () -> shoppingCartService.addToCart("test@gmail.com", addItemDTO));
    }


    @Test
    @DisplayName("Should fail when try to add a product that does not exists")
    void addToCartCase4() {
        List<CartProductDTO> cartProductDTOList = List.of(
                new CartProductDTO(1L, "Product 1", 1000L, 1L, ProductCategory.HOME),
                new CartProductDTO(2L, "Product 2", 2000L, 1L, ProductCategory.ELECTRONICS)
        );
        Long productId = 1L;
        Product product = initProduct(productId);

        AddItemDTO addItemDTO = new AddItemDTO(10000000000L, 1L);

        when(redisTemplate.opsForList()).thenReturn(listOps);
        when(redisTemplate.opsForList().range(anyString(), anyLong(), anyLong())).thenReturn(cartProductDTOList);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Assertions.assertThrowsExactly(NotFoundEntityException.class, () -> shoppingCartService.addToCart("test@gmail.com", addItemDTO));
    }

    private Product initProduct(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setPrice(1000L);
        product.setStock(2000L);
        product.setCategory(ProductCategory.HOME);
        return product;
    }
}