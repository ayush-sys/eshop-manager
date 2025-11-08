package com.example.eshop.repository;

import com.example.eshop.model.ProductCatalogMapping;
import com.example.eshop.model.ProductDetails;
import com.example.eshop.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for catalog-product mapping data.
 */
@Repository
public interface IProductCatalogMappingRepository extends JpaRepository<ProductCatalogMapping, Long> {

    /**
     * Finds mappings by catalog.
     */
    List<ProductCatalogMapping> findByCatalog(ProductCatalog catalog);

    /**
     * Finds mappings by product.
     */
    List<ProductCatalogMapping> findByProduct(ProductDetails product);

}
