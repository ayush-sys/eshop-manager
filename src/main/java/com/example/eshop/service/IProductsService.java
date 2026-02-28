package com.example.eshop.service;

import com.example.eshop.dao.model.ProductCatalog;
import com.example.eshop.dao.model.ProductDetails;
import com.example.eshop.utils.EShopResponse;

import java.util.List;

/**
 * Service interface for managing products and catalogs.
 */
public interface IProductsService {

    /**
     * Fetch all products from the system.
     */
    EShopResponse<List<ProductDetails>> fetchAllProducts();

    /**
     * Fetch product by its ID.
     *
     * @param id product ID
     */
    EShopResponse<ProductDetails> fetchProductById(Long id);

    /**
     * Add a new product to the system.
     *
     * @param product product details
     */
    EShopResponse<ProductDetails> addNewProduct(ProductDetails product);

    /**
     * Add a new catalog.
     *
     * @param catalog catalog details
     */
    EShopResponse<ProductCatalog> addNewCatalog(ProductCatalog catalog);

    /**
     * Fetch all products by catalog name.
     *
     * @param catalogName name of the catalog
     */
    EShopResponse<List<ProductDetails>> fetchProductByCatalog(String catalogName);

    /**
     * Update product details by product ID.
     *
     * @param id product ID
     * @param newData new product data
     */
    EShopResponse<ProductDetails> updateProductDetails(Long id, ProductDetails newData);

    /**
     * Update catalog association for a product.
     *
     * @param productId product ID
     * @param catalogName catalog name
     */
    EShopResponse<ProductDetails> updateProductCatalog(Long productId, String catalogName);

    /**
     * Update stock quantity for a product.
     *
     * @param id product ID
     * @param stock new stock count
     */
    EShopResponse<ProductDetails> updateStocksForProductById(Long id, int stock);

    /**
     * Delete product by ID.
     *
     * @param id product ID
     */
    EShopResponse<String> deleteProductById(Long id);

}
