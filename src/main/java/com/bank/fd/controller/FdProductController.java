package com.bank.fd.controller;

import com.bank.fd.entity.FdProduct;
import com.bank.fd.service.FdProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class FdProductController {

    private final FdProductService productService;

    public FdProductController(FdProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<FdProduct> createProduct(@Valid @RequestBody FdProduct product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<FdProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FdProduct> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FdProduct> updateProduct(@PathVariable Long id, @Valid @RequestBody FdProduct product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
