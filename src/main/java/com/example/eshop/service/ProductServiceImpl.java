package com.example.eshop.service;

import com.example.eshop.enums.AppEnums;
import com.example.eshop.model.ProductCatalog;
import com.example.eshop.model.ProductDetails;
import com.example.eshop.repository.IProductCatalogRepository;
import com.example.eshop.repository.IProductDetailsRepository;
import com.example.eshop.utils.ApiResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements IProductsService {

    private final IProductDetailsRepository productRepo;
    private final IProductCatalogRepository catalogRepo;

    @Autowired
    public ProductServiceImpl(IProductDetailsRepository productRepo, IProductCatalogRepository catalogRepo) {
        this.productRepo = productRepo;
        this.catalogRepo = catalogRepo;
    }

    @Override
    public ApiResponseWrapper<List<ProductDetails>> fetchAllProducts() {
        ApiResponseWrapper<List<ProductDetails>> response = new ApiResponseWrapper<>();
        try {
            List<ProductDetails> products = productRepo.findAll();
            if (products.isEmpty()) {
                log.info("No products found");
                response.errorMessage(AppEnums.NOT_FOUND);
            } else {
                response.successMessage(AppEnums.FOUND, products);
            }
        } catch (Exception e) {
            log.error("Fetch all error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<ProductDetails> fetchProductById(Long productId) {
        ApiResponseWrapper<ProductDetails> response = new ApiResponseWrapper<>();
        try {
            Optional<ProductDetails> product = productRepo.findById(productId);
            if (product.isPresent()) {
                response.successMessage(AppEnums.FOUND, product.get());
            } else {
                log.info("Product not found: {}", productId);
                response.errorMessage(AppEnums.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Fetch by id error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<ProductDetails> addNewProduct(ProductDetails productDetails) {
        ApiResponseWrapper<ProductDetails> response = new ApiResponseWrapper<>();
        try {
            productDetails.setStatus(AppEnums.CREATED);
            ProductDetails saved = productRepo.save(productDetails);
            log.info("Product added: {}", saved.getProductName());
            response.successMessage(AppEnums.CREATED, saved);
        } catch (Exception e) {
            log.error("Add product error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<ProductCatalog> addNewCatalog(ProductCatalog productCatalog) {
        ApiResponseWrapper<ProductCatalog> response = new ApiResponseWrapper<>();
        try {
            productCatalog.setStatus(AppEnums.CREATED);
            ProductCatalog saved = catalogRepo.save(productCatalog);
            log.info("Catalog added: {}", saved.getCatalogName());
            response.successMessage(AppEnums.CREATED, saved);
        } catch (Exception e) {
            log.error("Add catalog error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<List<ProductDetails>> fetchProductByCatalog(String catalogName) {
        ApiResponseWrapper<List<ProductDetails>> response = new ApiResponseWrapper<>();
        try {
            List<ProductDetails> products = productRepo.findByCatalogName(catalogName);
            if (products.isEmpty()) {
                log.info("No products found for catalog: {}", catalogName);
                response.errorMessage(AppEnums.CATEGORY_NOT_FOUND);
            } else {
                response.successMessage(AppEnums.FOUND, products);
            }
        } catch (Exception e) {
            log.error("Fetch by catalog error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<ProductDetails> updateProductDetails(Long productId, ProductDetails productDetails) {
        ApiResponseWrapper<ProductDetails> response = new ApiResponseWrapper<>();
        try {
            Optional<ProductDetails> existing = productRepo.findById(productId);
            if (existing.isPresent()) {
                ProductDetails product = existing.get();
                product.setProductName(productDetails.getProductName());
                product.setProductDescription(productDetails.getProductDescription());
                product.setPrice(productDetails.getPrice());
                product.setBrandName(productDetails.getBrandName());
                product.setModelNumber(productDetails.getModelNumber());
                product.setColor(productDetails.getColor());
                product.setUpdatedAt(LocalDateTime.now());
                product.setStatus(AppEnums.UPDATED);
                ProductDetails updated = productRepo.save(product);
                log.info("Product updated: {}", productId);
                response.successMessage(AppEnums.COMPLETED, updated);
            } else {
                response.errorMessage(AppEnums.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Update product error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<ProductDetails> updateProductCatalog(Long productId, String catalogName) {
        ApiResponseWrapper<ProductDetails> response = new ApiResponseWrapper<>();
        try {
            Optional<ProductDetails> productOpt = productRepo.findById(productId);
            if (productOpt.isEmpty()) {
                log.info("Product not found: {}", productId);
                response.errorMessage(AppEnums.NOT_FOUND);
                return response;
            }

            Optional<ProductCatalog> catalogOpt = catalogRepo.findByCatalogName(catalogName);
            if (catalogOpt.isEmpty()) {
                log.info("Catalog not found: {}", catalogName);
                response.errorMessage(AppEnums.CATEGORY_NOT_FOUND);
                return response;
            }

            ProductDetails product = productOpt.get();
            ProductCatalog catalog = catalogOpt.get();

            // Add mapping entry (Product <-> Catalog)
            product.getCatalogs().add(catalog);
            product.setUpdatedAt(LocalDateTime.now());
            product.setStatus(AppEnums.ADDED_TO_CATALOG);

            ProductDetails updatedProduct = productRepo.save(product);

            log.info("Product {} added to catalog {}", productId, catalogName);
            response.successMessage(AppEnums.ADDED_TO_CATALOG, updatedProduct);
        } catch (Exception e) {
            log.error("Error updating product catalog: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<ProductDetails> updateStocksForProductById(Long id, int stock) {
        ApiResponseWrapper<ProductDetails> response = new ApiResponseWrapper<>();
        try {
            Optional<ProductDetails> productOpt = productRepo.findById(id);
            if (productOpt.isEmpty()) {
                log.info("Product not found: {}", id);
                response.errorMessage(AppEnums.NOT_FOUND);
                return response;
            }

            ProductDetails product = productOpt.get();

            // Stock logic: if stock > 0 → IN_STOCK, else → OUT_OF_STOCK
            boolean inStock = stock > 0;
            product.setInStock(inStock);
            product.setUpdatedAt(LocalDateTime.now());
            product.setStatus(inStock ? AppEnums.IN_STOCK : AppEnums.OUT_OF_STOCK);

            ProductDetails updatedProduct = productRepo.save(product);

            log.info("Stock updated for product {} -> stock: {}", id, stock);
            response.successMessage(AppEnums.COMPLETED, updatedProduct);
        } catch (Exception e) {
            log.error("Error updating stock: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

    @Override
    public ApiResponseWrapper<String> deleteProductById(Long productId) {
        ApiResponseWrapper<String> response = new ApiResponseWrapper<>();
        try {
            if (productRepo.existsById(productId)) {
                productRepo.deleteById(productId);
                log.info("Product deleted: {}", productId);
                response.successMessage(AppEnums.DELETED, "Product deleted successfully");
            } else {
                response.errorMessage(AppEnums.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Delete product error: {}", e.getMessage());
            response.errorMessage(AppEnums.ERROR);
        }
        return response;
    }

}
