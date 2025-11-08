package com.example.eshop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "product_catalog")
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catalog_name", nullable = false, unique = true)
    private String catalogName;

    @Column(name = "catalog_description", length = 500)
    private String catalogDescription;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationship with ProductDetails
    @ManyToMany(mappedBy = "catalogs", fetch = FetchType.LAZY)
    private Set<ProductDetails> products;

}
