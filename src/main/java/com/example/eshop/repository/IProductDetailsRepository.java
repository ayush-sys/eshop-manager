package com.example.eshop.repository;

import com.example.eshop.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Product Details Repository.
 */
@Repository
public interface IProductDetailsRepository extends JpaRepository<ProductDetails, Long> {

    /**
     * Returns a list of products associated with the given catalog name.
     *
     * @param catalogName the name of the catalog
     */
    @Query("SELECT p FROM ProductDetails p JOIN p.catalogs c WHERE c.catalogName = :catalogName")
    List<ProductDetails> findByCatalogName(@Param("catalogName") String catalogName);

}
