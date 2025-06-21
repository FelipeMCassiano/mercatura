package com.felipemcassiano.Mercatura.repositories;

import com.felipemcassiano.Mercatura.models.product.Product;
import com.felipemcassiano.Mercatura.models.product.ProductCategory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filter(Integer min, Integer max, Integer quantity, ProductCategory category) {
        return (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            if (quantity != null) {
                predicates.add(cb.equal(root.get("quantity"), quantity));
            }
            if (min != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), min));
            }
            if (max != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), max));
            }
            if (category != null) {
                predicates.add(cb.equal(root.get("category"), category));
            }
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
