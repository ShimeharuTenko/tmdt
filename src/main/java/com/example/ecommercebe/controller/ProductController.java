package com.example.ecommercebe.controller;

import com.example.ecommercebe.dto.response.ProductResponse;
import com.example.ecommercebe.model.Product;
import com.example.ecommercebe.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Lấy tất cả sản phẩm
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // Lấy chi tiết theo slug
    @GetMapping("/{slug}")
    public ProductResponse getProductBySlug(@PathVariable String slug) {
        return productService.getProductBySlug(slug);
    }

    // Tạo sản phẩm
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Xoá sản phẩm
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "Deleted!";
    }
}


