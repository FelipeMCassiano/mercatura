package com.felipemcassiano.Mercatura.controllers;


import com.felipemcassiano.Mercatura.dtos.ApiResponseDTO;
import com.felipemcassiano.Mercatura.dtos.ProductDTO;
import com.felipemcassiano.Mercatura.dtos.ProductFilterDTO;
import com.felipemcassiano.Mercatura.dtos.ProductResponseDTO;
import com.felipemcassiano.Mercatura.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductDTO product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("filter")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> filterProducts(@RequestBody ProductFilterDTO request, @RequestParam("page") int page, @RequestParam("size") int size) {
        var response = productService.filter(request, page, size);
        if (response.items().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDTO product, @PathVariable Long id) {
        productService.update(product, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        var responseDTO = productService.findById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> get(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(productService.findAll(page, size), HttpStatus.OK);
    }
}
