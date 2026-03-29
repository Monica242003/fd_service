package com.bank.fd.service;

import com.bank.fd.entity.FdProduct;
import com.bank.fd.repository.FdProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FdProductService {

    private final FdProductRepository productRepository;

    public FdProductService(FdProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public FdProduct createProduct(FdProduct product) {
        return productRepository.save(product);
    }

    public List<FdProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public FdProduct getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public FdProduct updateProduct(Long id, FdProduct productDetails) {
        FdProduct product = getProductById(id);
        product.setName(productDetails.getName());
        product.setMinAmount(productDetails.getMinAmount());
        product.setMaxAmount(productDetails.getMaxAmount());
        product.setTenorMinMonths(productDetails.getTenorMinMonths());
        product.setTenorMaxMonths(productDetails.getTenorMaxMonths());
        product.setAnnualRate(productDetails.getAnnualRate());
        product.setCompounding(productDetails.getCompounding());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        FdProduct product = getProductById(id);
        productRepository.delete(product);
    }
}
