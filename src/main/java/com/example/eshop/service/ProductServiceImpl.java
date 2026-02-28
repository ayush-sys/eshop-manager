package com.example.eshop.service;

import com.example.eshop.dao.model.ProductCatalog;
import com.example.eshop.dao.model.ProductDetails;
import com.example.eshop.dao.repository.IProductCatalogRepository;
import com.example.eshop.dao.repository.IProductDetailsRepository;
import com.example.eshop.enums.AppEnums;
import com.example.eshop.utils.EShopResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductsService {

    private final IProductDetailsRepository productRepo;
    private final IProductCatalogRepository catalogRepo;

    @Autowired
    public ProductServiceImpl(IProductDetailsRepository productRepo, IProductCatalogRepository catalogRepo) {
        this.productRepo = productRepo;
        this.catalogRepo = catalogRepo;
    }

    @Override
    public EShopResponse<List<ProductDetails>> fetchAllProducts() {
        List<ProductDetails> products = productRepo.findAll();
        if (products.isEmpty()) {
            return EShopResponse.failure(AppEnums.NOT_FOUND, "No products found");
        }
        log.info("Fetched {} products successfully", products.size());
        return EShopResponse.success(AppEnums.FOUND, products);
    }

    @Override
    public EShopResponse<ProductDetails> fetchProductById(Long id) {
        return productRepo.findById(id)
                .map(product -> EShopResponse.success(AppEnums.FOUND, product))
                .orElse(EShopResponse.failure(AppEnums.NOT_FOUND, "Product not found"));
    }

    @Override
    public EShopResponse<ProductDetails> addNewProduct(ProductDetails productDetails) {
        try {
            catalogRepo.findByCatalogName(productDetails.getCatalogName())
                    .orElseThrow(() -> new RuntimeException("Catalog not found: " + productDetails.getCatalogName()));

            ProductDetails saved = productRepo.save(productDetails);
            log.info("Product '{}' added successfully under catalog '{}'", saved.getProductName(), saved.getCatalogName());
            return EShopResponse.success(AppEnums.CREATED, saved);
        } catch (Exception e) {
            log.error("Error adding product: {}", e.getMessage(), e);
            return EShopResponse.failure(AppEnums.ERROR, e.getMessage());
        }
    }

    @Override
    public EShopResponse<ProductCatalog> addNewCatalog(ProductCatalog catalog) {
        ProductCatalog savedCatalog = catalogRepo.save(catalog);
        log.info("Added new catalog: {}", savedCatalog.getCatalogName());
        return EShopResponse.success(AppEnums.SUCCESS, savedCatalog);
    }

    @Override
    public EShopResponse<List<ProductDetails>> fetchProductByCatalog(String catalogName) {
        var optionalCatalog = catalogRepo.findByCatalogName(catalogName);

        if (optionalCatalog.isEmpty()) {
            return EShopResponse.failure(AppEnums.NOT_FOUND, "Catalog not found: " + catalogName);
        }

        ProductCatalog catalog = optionalCatalog.get();
        List<ProductDetails> products = productRepo.findByCatalogId(catalog.getCatalogId());

        if (products.isEmpty()) {
            return EShopResponse.failure(AppEnums.NOT_FOUND, "No products found for catalog: " + catalogName);
        }

        log.info("Fetched {} products for catalog '{}'", products.size(), catalogName);
        return EShopResponse.success(AppEnums.FOUND, products);
    }

    @Override
    public EShopResponse<ProductDetails> updateProductDetails(Long id, ProductDetails newData) {
        return productRepo.findById(id).map(existing -> {
            existing.setProductName(newData.getProductName());
            existing.setDescription(newData.getDescription());
            existing.setSku(newData.getSku());
            existing.setMakerName(newData.getMakerName());
            existing.setModelNumber(newData.getModelNumber());
            existing.setPrice(newData.getPrice());
            existing.setQuantityInStock(newData.getQuantityInStock());
            existing.setInStock(newData.getInStock());
            existing.setDiscountPercent(newData.getDiscountPercent());
            existing.setDimensions(newData.getDimensions());
            existing.setWeight(newData.getWeight());
            existing.setStatus(newData.getStatus());
            existing.setCatalogId(newData.getCatalogId());

            ProductDetails updated = productRepo.save(existing);
            log.info("Updated product with ID {}", id);
            return EShopResponse.success(AppEnums.UPDATED, updated);
        }).orElse(EShopResponse.failure(AppEnums.NOT_FOUND, "Product not found"));
    }

    @Override
    public EShopResponse<ProductDetails> updateProductCatalog(Long productId, String catalogName) {
        return productRepo.findById(productId).map(product -> {
            ProductCatalog catalog = catalogRepo.findByCatalogName(catalogName)
                    .orElseGet(() -> {
                        ProductCatalog newCatalog = new ProductCatalog();
                        newCatalog.setCatalogName(catalogName);
                        newCatalog.setStatus(AppEnums.ACTIVE);
                        return catalogRepo.save(newCatalog);
                    });
            product.setCatalogId(catalog.getCatalogId());
            ProductDetails updated = productRepo.save(product);
            log.info("Updated catalog '{}' for product ID {}", catalogName, productId);
            return EShopResponse.success(AppEnums.UPDATED, updated);
        }).orElse(EShopResponse.failure(AppEnums.NOT_FOUND, "Product not found"));
    }

    @Override
    public EShopResponse<ProductDetails> updateStocksForProductById(Long id, int stock) {
        return productRepo.findById(id).map(product -> {
            product.setQuantityInStock(stock);
            product.setInStock(stock > 0);
            ProductDetails updated = productRepo.save(product);
            log.info("Updated stock for product ID {} to {}", id, stock);
            return EShopResponse.success(AppEnums.UPDATED, updated);
        }).orElse(EShopResponse.failure(AppEnums.NOT_FOUND, "Product not found"));
    }

    @Override
    public EShopResponse<String> deleteProductById(Long id) {
        if (!productRepo.existsById(id)) {
            return EShopResponse.failure(AppEnums.NOT_FOUND, "Product not found");
        }
        productRepo.deleteById(id);
        log.info("Deleted product ID {}", id);
        return EShopResponse.success(AppEnums.DELETED, "Product deleted successfully");
    }

}
