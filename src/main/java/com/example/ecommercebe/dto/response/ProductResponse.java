package com.example.ecommercebe.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponse {

    private String id;
    private String name;
    private String slug;
    private String sku;
    private BigDecimal price;
    private Boolean isPublished;
    private String shortDescription;
    private List<String> images;
    private List<ProductVariantResponse> variants;
    private String thumbnail;

    // GETTERS

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getSku() { return sku; }
    public BigDecimal getPrice() { return price; }
    public Boolean getIsPublished() { return isPublished; }
    public String getShortDescription() { return shortDescription; }
    public List<String> getImages() { return images; }
    public List<ProductVariantResponse> getVariants() { return variants; }

    // SETTERS

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSlug(String slug) { this.slug = slug; }
    public void setSku(String sku) { this.sku = sku; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public void setImages(List<String> images) { this.images = images; }
    public void setVariants(List<ProductVariantResponse> variants) { this.variants = variants; }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
