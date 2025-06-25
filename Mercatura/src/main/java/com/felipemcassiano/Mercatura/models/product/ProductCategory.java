package com.felipemcassiano.Mercatura.models.product;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductCategory {
    ELECTRONICS("electronics"),
    FASHION("fashion"),
    HOME("home"),
    PERSONAL_CARE("personal_care"),
    SPORTS("sports"),
    MEDIA("media"),
    ;


    private String category;

    ProductCategory(String category) {
        this.category = category;
    }

    @JsonValue
    public String getCategory() {
        return category;
    }
}
