package com.example.eshop.controller;

import com.example.eshop.dao.model.ProductCatalog;
import com.example.eshop.dao.model.ProductDetails;
import com.example.eshop.service.IProductsService;
import com.example.eshop.utils.EShopResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private IProductsService productService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void fetchAllProducts_success() throws Exception {
    EShopResponse<List<ProductDetails>> response = new EShopResponse<>("OK", "Success", Collections.emptyList());

    Mockito.when(productService.fetchAllProducts()).thenReturn(response);

    mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("Success"));
  }

  @Test
  void fetchProductById_success() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setProductId(1L);

    EShopResponse<ProductDetails> response = new EShopResponse<>("OK", "Success", product);

    Mockito.when(productService.fetchProductById(1L)).thenReturn(response);

    mockMvc.perform(get("/api/products/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.data.productId").value(1L));
  }

  @Test
  void addNewProduct_success() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setProductName("Mobile");

    EShopResponse<ProductDetails> response = new EShopResponse<>("OK", "Created", product);

    Mockito.when(productService.addNewProduct(Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"));
  }

  @Test
  void addNewCatalog_success() throws Exception {
    ProductCatalog catalog = new ProductCatalog();
    catalog.setCatalogName("Electronics");

    EShopResponse<ProductCatalog> response = new EShopResponse<>("OK", "Created", catalog);

    Mockito.when(productService.addNewCatalog(Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(post("/api/products/catalog")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(catalog)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.data.catalogName").value("Electronics"));
  }

  @Test
  void fetchProductByCatalog_success() throws Exception {
    EShopResponse<List<ProductDetails>> response = new EShopResponse<>("OK", "Success", Collections.emptyList());

    Mockito.when(productService.fetchProductByCatalog("Electronics"))
        .thenReturn(response);

    mockMvc.perform(get("/api/products/catalog/{catalogName}", "Electronics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"));
  }

  @Test
  void updateProductDetails_success() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setProductName("Updated Product");

    EShopResponse<ProductDetails> response = new EShopResponse<>("OK", "Updated", product);

    Mockito.when(productService.updateProductDetails(Mockito.eq(1L), Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{productId}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"));
  }

  @Test
  void updateProductCatalog_success() throws Exception {
    EShopResponse<ProductDetails> response = new EShopResponse<>("OK", "Updated", new ProductDetails());

    Mockito.when(productService.updateProductCatalog(1L, "Electronics"))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{productId}/catalog/{catalogName}", 1L, "Electronics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"));
  }

  @Test
  void updateStock_success() throws Exception {
    EShopResponse<ProductDetails> response = new EShopResponse<>("OK", "Stock Updated", new ProductDetails());

    Mockito.when(productService.updateStocksForProductById(1L, 10))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{id}/stock/{stock}", 1L, 10))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"));
  }

  @Test
  void deleteProductById_success() throws Exception {
    EShopResponse<String> response = new EShopResponse<>("OK", "Deleted", "Product removed");

    Mockito.when(productService.deleteProductById(1L))
        .thenReturn(response);

    mockMvc.perform(delete("/api/products/{productId}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.data").value("Product removed"));
  }

  @Test
  void fetchAllProducts_noProducts() throws Exception {
    EShopResponse<List<ProductDetails>> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "No products found", null);

    Mockito.when(productService.fetchAllProducts()).thenReturn(response);

    mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).fetchAllProducts();
  }

  @Test
  void fetchProductById_notFound() throws Exception {
    EShopResponse<ProductDetails> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Product not found", null);

    Mockito.when(productService.fetchProductById(99L)).thenReturn(response);

    mockMvc.perform(get("/api/products/{id}", 99L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).fetchProductById(99L);
  }

  @Test
  void addNewProduct_catalogNotFound() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setProductName("Mobile");
    product.setCatalogName("Unknown");

    EShopResponse<ProductDetails> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Catalog not found", null);

    Mockito.when(productService.addNewProduct(Mockito.any())).thenReturn(response);

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).addNewProduct(Mockito.any());
  }

  @Test
  void fetchProductByCatalog_notFound() throws Exception {
    EShopResponse<List<ProductDetails>> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Catalog not found: Unknown", null);

    Mockito.when(productService.fetchProductByCatalog("Unknown")).thenReturn(response);

    mockMvc.perform(get("/api/products/catalog/{catalogName}", "Unknown"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).fetchProductByCatalog("Unknown");
  }

  @Test
  void updateProductDetails_notFound() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setProductName("Updated Product");

    EShopResponse<ProductDetails> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Product not found", null);

    Mockito.when(productService.updateProductDetails(Mockito.eq(99L), Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{productId}", 99L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).updateProductDetails(Mockito.eq(99L), Mockito.any());
  }

  @Test
  void updateProductCatalog_notFound() throws Exception {
    EShopResponse<ProductDetails> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Product not found", null);

    Mockito.when(productService.updateProductCatalog(99L, "Electronics"))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{productId}/catalog/{catalogName}", 99L, "Electronics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).updateProductCatalog(99L, "Electronics");
  }

  @Test
  void updateStock_notFound() throws Exception {
    EShopResponse<ProductDetails> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Product not found", null);

    Mockito.when(productService.updateStocksForProductById(99L, 10))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{id}/stock/{stock}", 99L, 10))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).updateStocksForProductById(99L, 10);
  }

  @Test
  void deleteProductById_notFound() throws Exception {
    EShopResponse<String> response =
        new EShopResponse<>("INTERNAL_SERVER_ERROR", "Product not found", null);

    Mockito.when(productService.deleteProductById(99L)).thenReturn(response);

    mockMvc.perform(delete("/api/products/{productId}", 99L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));

    Mockito.verify(productService).deleteProductById(99L);
  }

  @Test
  void addNewCatalog_verifiesServiceInvocation() throws Exception {
    ProductCatalog catalog = new ProductCatalog();
    catalog.setCatalogName("Electronics");

    EShopResponse<ProductCatalog> response = new EShopResponse<>("OK", "Created", catalog);

    Mockito.when(productService.addNewCatalog(Mockito.any())).thenReturn(response);

    mockMvc.perform(post("/api/products/catalog")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(catalog)))
        .andExpect(status().isOk());

    Mockito.verify(productService).addNewCatalog(Mockito.any());
  }
}
