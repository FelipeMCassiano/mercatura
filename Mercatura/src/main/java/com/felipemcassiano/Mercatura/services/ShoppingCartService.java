package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.dtos.AddItemDTO;
import com.felipemcassiano.Mercatura.infra.CartProductDTO;
import com.felipemcassiano.Mercatura.infra.CheckoutResponseDTO;
import com.felipemcassiano.Mercatura.infra.exceptions.NotEnoughStockException;
import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.models.shoppingCart.ShoppingCart;
import com.felipemcassiano.Mercatura.repositories.ProductRepository;
import com.felipemcassiano.Mercatura.repositories.UserRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    private final ListOperations<String, CartProductDTO> redisListOps;
    private final ProductRepository productRepository;
    private final StripeService stripeService;


    public ShoppingCartService(RedisTemplate<String, CartProductDTO> redisTemplate, ProductRepository productRepository, UserRepository userRepository, ProductRepository productRepository1, StripeService stripeService) {
        this.redisListOps = redisTemplate.opsForList();
        this.productRepository = productRepository1;
        this.stripeService = stripeService;
    }

    public void addToCart(String userEmail, AddItemDTO dto) {
        String key = String.format("cart:%s", userEmail);

        List<CartProductDTO> cart = redisListOps.range(key, 0, -1);
        Optional<Product> product = productRepository.findById(dto.productId());

        if (product.isEmpty()) return;

        if ((product.get().getStock() - 1) < 1) {
            throw new NotEnoughStockException();
        }

        if (cart != null) {
            for (CartProductDTO cartProduct : cart) {
                if (dto.productId().equals(cartProduct.id())) {
                    var updatedCartProduct = new CartProductDTO(cartProduct.id(), cartProduct.name(), cartProduct.price(), cartProduct.quantity() + dto.quantity(), cartProduct.category());
                    redisListOps.remove(key, 1, cartProduct);
                    redisListOps.rightPush(key, updatedCartProduct);
                    return;
                }
            }
        }

        CartProductDTO productToBeCached = new CartProductDTO(product.get().getId(), product.get().getName(), product.get().getPrice(), dto.quantity(), product.get().getCategory());
        redisListOps.rightPush(key, productToBeCached);
    }

    public ShoppingCart getCartByUser(String userEmail) {
        return getShoppingCartByUser(userEmail);
    }

    public CheckoutResponseDTO checkout(String userEmail) {
        ShoppingCart shoppingCartDTO = getShoppingCartByUser(userEmail);
        return stripeService.checkout(shoppingCartDTO);
    }

    private ShoppingCart getShoppingCartByUser(String userEmail) {
        String key = String.format("cart:%s", userEmail);

        List<CartProductDTO> cart = redisListOps.range(key, 0, -1);

        Long total = cart != null ? cart.stream().map(x -> x.price() * x.quantity()).reduce(0l, Long::sum) : 0;

        return new ShoppingCart(cart, total);
    }

}
