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
}
