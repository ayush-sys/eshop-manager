package com.example.eshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_catalog_mapping")
public class ProductCatalogMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetails product;

    @ManyToOne
    @JoinColumn(name = "catalog_id", nullable = false)
    private ProductCatalog catalog;

}