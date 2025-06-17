package com.felipemcassiano.Mercatura.models.product;

public enum ProductCategory {
    ELECTRONICS("eletronics"),
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

    public String getCategory() {
        return category;
    }
}
