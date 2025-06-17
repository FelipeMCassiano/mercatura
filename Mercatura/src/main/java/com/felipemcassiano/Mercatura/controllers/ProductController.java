package com.felipemcassiano.Mercatura.controllers;


import com.felipemcassiano.Mercatura.models.product.ProductDTO;
import com.felipemcassiano.Mercatura.models.product.ProductFilterDTO;
import com.felipemcassiano.Mercatura.models.product.ProductResponseDTO;
import com.felipemcassiano.Mercatura.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Void> createProduct(@RequestBody ProductDTO product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(@RequestBody ProductFilterDTO request) {
        var response = productService.filter(request);
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDTO product, @PathVariable Long id) {
        productService.update(product, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id]")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        var responseDTO = productService.findById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // add pagination
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> get() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
}
