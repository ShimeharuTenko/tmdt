package com.example.ecommercebe.repository;

import com.example.ecommercebe.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findBySlug(String slug);

    boolean existsBySlug(String slug);
}

