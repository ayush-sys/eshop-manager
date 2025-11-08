package com.example.eshop.service;

import com.example.eshop.model.ProductCatalog;
import com.example.eshop.model.ProductDetails;
import com.example.eshop.utils.ApiResponseWrapper;

import java.util.List;

/**
 * Service interface for managing products and catalogs.
 */
public interface IProductsService {

    /**
     * Fetch all products from the system.
     */
    ApiResponseWrapper<List<ProductDetails>> fetchAllProducts();

    /**
     * Fetch product by its ID.
     *
     * @param id product ID
     */
    ApiResponseWrapper<ProductDetails> fetchProductById(Long id);

    /**
     * Add a new product to the system.
     *
     * @param product product details
     */
    ApiResponseWrapper<ProductDetails> addNewProduct(ProductDetails product);

    /**
     * Add a new catalog.
     *
     * @param catalog catalog details
     */
    ApiResponseWrapper<ProductCatalog> addNewCatalog(ProductCatalog catalog);

    /**
     * Fetch all products by catalog name.
     *
     * @param catalogName name of the catalog
     */
    ApiResponseWrapper<List<ProductDetails>> fetchProductByCatalog(String catalogName);

    /**
     * Update product details by product ID.
     *
     * @param id product ID
     * @param newData new product data
     */
    ApiResponseWrapper<ProductDetails> updateProductDetails(Long id, ProductDetails newData);

    /**
     * Update catalog association for a product.
     *
     * @param productId product ID
     * @param catalogName catalog name
     */
    ApiResponseWrapper<ProductDetails> updateProductCatalog(Long productId, String catalogName);

    /**
     * Update stock quantity for a product.
     *
     * @param id product ID
     * @param stock new stock count
     */
    ApiResponseWrapper<ProductDetails> updateStocksForProductById(Long id, int stock);

    /**
     * Delete product by ID.
     *
     * @param id product ID
     */
    ApiResponseWrapper<String> deleteProductById(Long id);

}
