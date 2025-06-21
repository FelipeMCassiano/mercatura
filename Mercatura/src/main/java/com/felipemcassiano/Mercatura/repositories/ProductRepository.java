package com.felipemcassiano.Mercatura.repositories;

import com.felipemcassiano.Mercatura.models.product.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Boolean existsByName(String name);
}
