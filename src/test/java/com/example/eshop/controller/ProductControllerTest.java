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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IProductsService productService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void fetchAllProducts_success() throws Exception {
    EShopResponse<List<ProductDetails>> response = new EShopResponse<>(true, "Success", Collections.emptyList());

    Mockito.when(productService.fetchAllProducts()).thenReturn(response);

    mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void fetchProductById_success() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setId(1L);

    EShopResponse<ProductDetails> response = new EShopResponse<>(true, "Success", product);

    Mockito.when(productService.fetchProductById(1L)).thenReturn(response);

    mockMvc.perform(get("/api/products/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id").value(1L));
  }

  @Test
  void addNewProduct_success() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setName("Mobile");

    EShopResponse<ProductDetails> response = new EShopResponse<>(true, "Created", product);

    Mockito.when(productService.addNewProduct(Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void addNewCatalog_success() throws Exception {
    ProductCatalog catalog = new ProductCatalog();
    catalog.setName("Electronics");

    EShopResponse<ProductCatalog> response = new EShopResponse<>(true, "Created", catalog);

    Mockito.when(productService.addNewCatalog(Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(post("/api/products/catalog")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(catalog)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.name").value("Electronics"));
  }

  @Test
  void fetchProductByCatalog_success() throws Exception {
    EShopResponse<List<ProductDetails>> response = new EShopResponse<>(true, "Success", Collections.emptyList());

    Mockito.when(productService.fetchProductByCatalog("Electronics"))
        .thenReturn(response);

    mockMvc.perform(get("/api/products/catalog/{catalogName}", "Electronics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void updateProductDetails_success() throws Exception {
    ProductDetails product = new ProductDetails();
    product.setName("Updated Product");

    EShopResponse<ProductDetails> response = new EShopResponse<>(true, "Updated", product);

    Mockito.when(productService.updateProductDetails(Mockito.eq(1L), Mockito.any()))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{productId}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void updateProductCatalog_success() throws Exception {
    EShopResponse<ProductDetails> response = new EShopResponse<>(true, "Updated", new ProductDetails());

    Mockito.when(productService.updateProductCatalog(1L, "Electronics"))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{productId}/catalog/{catalogName}", 1L, "Electronics"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void updateStock_success() throws Exception {
    EShopResponse<ProductDetails> response = new EShopResponse<>(true, "Stock Updated", new ProductDetails());

    Mockito.when(productService.updateStocksForProductById(1L, 10))
        .thenReturn(response);

    mockMvc.perform(put("/api/products/{id}/stock/{stock}", 1L, 10))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void deleteProductById_success() throws Exception {
    EShopResponse<String> response = new EShopResponse<>(true, "Deleted", "Product removed");

    Mockito.when(productService.deleteProductById(1L))
        .thenReturn(response);

    mockMvc.perform(delete("/api/products/{productId}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value("Product removed"));
  }
}
