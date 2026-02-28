package com.example.eshop.dao.repository;

import com.example.eshop.dao.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Product Details operations.
 */
@Repository
public interface IProductDetailsRepository extends JpaRepository<ProductDetails, Long> {

    /**
     * Fetch products by catalog ID.
     *
     * @param catalogId the ID of the catalog
     */
    List<ProductDetails> findByCatalogId(Long catalogId);

    /**
     * Fetch products using catalog name (through catalog table).
     *
     * @param catalogName the name of the catalog
     */
    @Query(value = "SELECT p.* FROM product_details p INNER JOIN product_catalog_details c ON p.catalog_id = c.catalog_id WHERE c.catalog_name = :catalogName",
            nativeQuery = true)
    List<ProductDetails> findByCatalogName(String catalogName);

}
