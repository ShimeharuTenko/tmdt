package com.example.ecommercebe.service;

import com.example.ecommercebe.dto.response.ProductResponse;
import com.example.ecommercebe.dto.response.ProductVariantResponse;
import com.example.ecommercebe.model.Product;
import com.example.ecommercebe.model.ProductImage;
import com.example.ecommercebe.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ------------------ MAPPING DTO -------------------

    private ProductResponse mapToDTO(Product product) {
        ProductResponse dto = new ProductResponse();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setSku(product.getSku());
        dto.setPrice(product.getPrice());
        dto.setIsPublished(product.getIsPublished());
        dto.setShortDescription(product.getShortDescription());
        dto.setThumbnail(product.getThumbnail());
        dto.setStock(product.getStock());

        // Images
        if (product.getImages() != null) {
            dto.setImages(
                    product.getImages()
                            .stream()
                            .map(ProductImage::getFilePath)
                            .collect(Collectors.toList())
            );
        }

        // Variants
        if (product.getVariants() != null) {
            dto.setVariants(
                    product.getVariants().stream()
                            .map(variant -> {
                                ProductVariantResponse v = new ProductVariantResponse();
                                v.setId(variant.getId());
                                v.setVariantName(variant.getVariantName());
                                v.setPrice(variant.getPrice());
                                v.setStock(variant.getStock());
                                return v;
                            })
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // ------------------ SERVICE LOGIC -------------------

    // 🔹 Lấy tất cả sản phẩm (Trang chủ)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(Product::getIsPublished)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Lấy chi tiết sản phẩm theo slug
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToDTO(product);
    }

    // 🔹 Tạo sản phẩm (admin)
    public Product createProduct(Product product) {
        if (productRepository.existsBySlug(product.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        return productRepository.save(product);
    }

    // 🔹 Xoá sản phẩm
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
