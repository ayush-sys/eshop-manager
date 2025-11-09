package com.example.eshop.repository;

import com.example.eshop.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Product Details operations.
 */
@Repository
public interface IProductDetailsRepository extends JpaRepository<ProductDetails, Long> {

    /**
     * Fetch all products belonging to a specific catalog name.
     *
     * @param catalogName name of the catalog
     * @return list of products under the given catalog
     */
    @Query("SELECT p FROM ProductDetails p WHERE p.catalog.catalogName = :catalogName")
    List<ProductDetails> findByCatalogName(@Param("catalogName") String catalogName);

}
