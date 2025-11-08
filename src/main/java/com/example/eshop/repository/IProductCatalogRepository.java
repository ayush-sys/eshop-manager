package com.example.eshop.repository;

import com.example.eshop.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing product catalogs.
 */
@Repository
public interface IProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {

    /**
     * Finds catalog by catalog name.
     *
     * @param catalogName catalog name
     * @return optional of ProductCatalog
     */
    Optional<ProductCatalog> findByCatalogName(String catalogName);

}
