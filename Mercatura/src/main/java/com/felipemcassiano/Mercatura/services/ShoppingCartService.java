package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.models.product.ProductResponseDTO;
import com.felipemcassiano.Mercatura.models.shoppingCart.ShoppingCartDTO;
import com.felipemcassiano.Mercatura.repositories.ProductRepository;
import com.felipemcassiano.Mercatura.repositories.UserRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {
    private final ListOperations<String, Object> redisListOps;
    private final ProductRepository productRepository;

    public ShoppingCartService(RedisTemplate<String, Object> redisTemplate, ProductRepository productRepository, UserRepository userRepository, ProductRepository productRepository1) {
        this.redisListOps = redisTemplate.opsForList();
        this.productRepository = productRepository1;
    }

    public void addToCart(String userEmail, Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            ProductResponseDTO productToBeCached = new ProductResponseDTO(product.get().getId(), product.get().getName(), product.get().getPrice(), product.get().getQuantity(), product.get().getCategory());
            redisListOps.rightPush(userEmail, productToBeCached);
        }
    }

    public ShoppingCartDTO getCartByUser(String userEmail) {


        return new ShoppingCartDTO(redisListOps.range(userEmail, 0, -1).stream().map(x -> (ProductResponseDTO) x).toList());
    }

}
