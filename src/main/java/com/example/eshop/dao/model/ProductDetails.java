package com.example.eshop.dao.model;

import com.example.eshop.enums.AppEnums;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "maker_name", length = 100)
    private String makerName;

    @Column(name = "model_number", length = 100)
    private String modelNumber;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "in_stock", nullable = false)
    private Boolean inStock = false;

    @Column(name = "discount_percent", precision = 5, scale = 2)
    private BigDecimal discountPercent = BigDecimal.ZERO;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock = 0;

    @Column(name = "weight", precision = 6, scale = 2)
    private BigDecimal weight;

    @Column(name = "dimensions", length = 100)
    private String dimensions;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AppEnums status = AppEnums.ACTIVE;

    @Column(name = "catalog_name", nullable = false, length = 150)
    private String catalogName;

    @Column(name = "catalog_id", nullable = false)
    private Long catalogId;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

}