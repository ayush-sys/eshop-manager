package com.example.eshop.model;

import com.example.eshop.enums.AppEnums;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;


/**
 * The Product Details.
 */
@Data
@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description", length = 1000)
    private String productDescription;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "color")
    private String color;

    @Column(name = "in_stock", nullable = false)
    private Boolean inStock = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationship with ProductCatalog
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_catalog_mapping",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "catalog_id")
    )
    private Set<ProductCatalog> catalogs;

    @Enumerated(EnumType.STRING)
    private AppEnums status;

}
