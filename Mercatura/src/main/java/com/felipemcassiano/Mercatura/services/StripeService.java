package com.felipemcassiano.Mercatura.services;

import com.felipemcassiano.Mercatura.dtos.ProductPriceRangeFilterDTO;
import com.felipemcassiano.Mercatura.dtos.UserDTO;
import com.felipemcassiano.Mercatura.infra.exceptions.InternalException;
import com.felipemcassiano.Mercatura.models.shoppingCart.ShoppingCart;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {
    @Value("${api.stripe.secretKey}")
    private String secretKey;

    @Value("${api.currency}")
    private String currency;


    public ProductPriceRangeFilterDTO.CheckoutResponseDTO checkout(ShoppingCart shoppingCart) {
        Stripe.apiKey = secretKey;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (UserDTO.CartProductDTO p : shoppingCart.products()) {
            lineItems.add(createLineItem(p));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/checkout/success")
                .setCancelUrl("http://localhost:8080/checkout/cancel")
                .build();

        try {
            Session session = Session.create(params);
            return new ProductPriceRangeFilterDTO.CheckoutResponseDTO("SUCCESS", "Payment session created", session.getId(), session.getUrl());
        } catch (StripeException ex) {
            throw new InternalException(ex.getMessage());
        }
    }

    private SessionCreateParams.LineItem createLineItem(UserDTO.CartProductDTO product) {
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                .builder()
                .setName(product.name())
                .build();
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                .builder()
                .setCurrency(currency)
                .setUnitAmount(product.price())
                .setProductData(productData)
                .build();

        return SessionCreateParams.LineItem
                .builder()
                .setQuantity(product.quantity())
                .setPriceData(priceData)
                .build();
    }
}
