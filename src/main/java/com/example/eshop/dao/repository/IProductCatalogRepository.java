package com.example.eshop.dao.repository;

import com.example.eshop.dao.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing product catalogs.
 */
@Repository
public interface IProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {

    /**
     * Find catalog by catalog name.
     */
    Optional<ProductCatalog> findByCatalogName(String catalogName);

}
