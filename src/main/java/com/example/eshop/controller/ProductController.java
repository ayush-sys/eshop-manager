package com.example.eshop.controller;

import com.example.eshop.dao.model.ProductCatalog;
import com.example.eshop.dao.model.ProductDetails;
import com.example.eshop.service.IProductsService;
import com.example.eshop.utils.EShopResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "Operations related to managing products and catalogs")
public class ProductController {

    private final IProductsService productService;

    @Autowired
    public ProductController(IProductsService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Fetch all products", description = "Retrieves the list of all available products.")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @GetMapping
    public ResponseEntity<EShopResponse<List<ProductDetails>>> fetchAllProducts() {
        return ResponseEntity.ok(productService.fetchAllProducts());
    }

    @Operation(summary = "Fetch product by ID", description = "Retrieves product details based on the provided product ID.")
    @ApiResponse(responseCode = "200", description = "Product found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<EShopResponse<ProductDetails>> fetchProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.fetchProductById(id));
    }

    @Operation(summary = "Add a new product", description = "Creates and stores a new product record.")
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @PostMapping
    public ResponseEntity<EShopResponse<ProductDetails>> addNewProduct(@RequestBody ProductDetails productDetails) {
        return ResponseEntity.ok(productService.addNewProduct(productDetails));
    }

    @Operation(summary = "Add a new product catalog", description = "Creates and stores a new catalog record.")
    @ApiResponse(responseCode = "201", description = "Catalog created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @PostMapping("/catalog")
    public ResponseEntity<EShopResponse<ProductCatalog>> addNewCatalog(@RequestBody ProductCatalog catalog) {
        return ResponseEntity.ok(productService.addNewCatalog(catalog));
    }

    @Operation(summary = "Fetch products by catalog name", description = "Retrieves all products associated with a given catalog.")
    @ApiResponse(responseCode = "200", description = "Products found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @GetMapping("/catalog/{catalogName}")
    public ResponseEntity<EShopResponse<List<ProductDetails>>> fetchProductByCatalog(@PathVariable String catalogName) {
        return ResponseEntity.ok(productService.fetchProductByCatalog(catalogName));
    }

    @Operation(summary = "Update product details", description = "Updates an existing product by product ID.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @PutMapping("/{productId}")
    public ResponseEntity<EShopResponse<ProductDetails>> updateProductDetails(
            @PathVariable Long productId,
            @RequestBody ProductDetails productDetails) {
        return ResponseEntity.ok(productService.updateProductDetails(productId, productDetails));
    }

    @Operation(summary = "Update product catalog", description = "Assigns a catalog to a specific product.")
    @ApiResponse(responseCode = "200", description = "Product catalog updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @PutMapping("/{productId}/catalog/{catalogName}")
    public ResponseEntity<EShopResponse<ProductDetails>> updateProductCatalog(
            @PathVariable Long productId,
            @PathVariable String catalogName) {
        return ResponseEntity.ok(productService.updateProductCatalog(productId, catalogName));
    }

    @Operation(summary = "Update stock count for a product", description = "Updates stock quantity for the given product ID.")
    @ApiResponse(responseCode = "200", description = "Stock updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @PutMapping("/{id}/stock/{stock}")
    public ResponseEntity<EShopResponse<ProductDetails>> updateStocksForProductById(
            @PathVariable Long id,
            @PathVariable int stock) {
        return ResponseEntity.ok(productService.updateStocksForProductById(id, stock));
    }

    @Operation(summary = "Delete a product by ID", description = "Removes a product from the catalog permanently.")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EShopResponse.class)))
    @DeleteMapping("/{productId}")
    public ResponseEntity<EShopResponse<String>> deleteProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }

}
