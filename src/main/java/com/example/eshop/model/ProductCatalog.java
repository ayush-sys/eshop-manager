package com.example.eshop.model;

import com.example.eshop.enums.AppEnums;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "product_catalog_details", indexes = {
        @Index(name = "idx_catalog_name", columnList = "catalog_name")
})
public class ProductCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id")
    private Long catalogId;

    @Column(name = "catalog_name", nullable = false, unique = true, length = 100)
    private String catalogName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_catalog_id")
    private ProductCatalog parentCatalog;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AppEnums status = AppEnums.ACTIVE;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductDetails> products;

}
