package com.example.eshop.service;

import com.example.eshop.dao.model.ProductCatalog;
import com.example.eshop.dao.model.ProductDetails;
import com.example.eshop.dao.repository.IProductCatalogRepository;
import com.example.eshop.dao.repository.IProductDetailsRepository;
import com.example.eshop.enums.AppEnums;
import com.example.eshop.utils.EShopResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductDetailsRepository productRepo;

    @Mock
    private IProductCatalogRepository catalogRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDetails product;

    private ProductCatalog catalog;

    @BeforeEach
    void setUp() {
        product = new ProductDetails();
        product.setProductId(1L);
        product.setProductName("iPhone");
        product.setCatalogName("Mobiles");
        product.setCatalogId(100L);
        product.setQuantityInStock(5);
        product.setInStock(true);

        catalog = new ProductCatalog();
        catalog.setCatalogId(100L);
        catalog.setCatalogName("Mobiles");
    }

    @Test
    void fetchAllProducts_success() {
        when(productRepo.findAll()).thenReturn(List.of(product));

        EShopResponse<List<ProductDetails>> response = productService.fetchAllProducts();

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.FOUND.toString()));
        assertEquals(1, response.getData().size());
        verify(productRepo).findAll();
    }

    @Test
    void fetchAllProducts_noProducts() {
        when(productRepo.findAll()).thenReturn(List.of());

        EShopResponse<List<ProductDetails>> response = productService.fetchAllProducts();

        assertEquals("INTERNAL_SERVER_ERROR", response.getStatus());
        assertTrue(response.getMessage().contains("No products found"));
    }

    @Test
    void fetchProductById_found() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        EShopResponse<ProductDetails> response = productService.fetchProductById(1L);

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.FOUND.toString()));
        assertEquals("iPhone", response.getData().getProductName());
    }

    @Test
    void fetchProductById_notFound() {
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        EShopResponse<ProductDetails> response = productService.fetchProductById(1L);

        assertEquals("INTERNAL_SERVER_ERROR", response.getStatus());
        assertTrue(response.getMessage().contains("Product not found"));
    }

    @Test
    void addNewProduct_success() {
        when(catalogRepo.findByCatalogName("Mobiles")).thenReturn(Optional.of(catalog));
        when(productRepo.save(product)).thenReturn(product);

        EShopResponse<ProductDetails> response = productService.addNewProduct(product);

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.CREATED.toString()));
        verify(productRepo).save(product);
    }

    @Test
    void addNewProduct_catalogNotFound() {
        ProductDetails invalidProduct = new ProductDetails();
        invalidProduct.setProductName("Samsung");
        invalidProduct.setCatalogName("InvalidCatalog");

        when(catalogRepo.findByCatalogName("InvalidCatalog")).thenReturn(Optional.empty());

        EShopResponse<ProductDetails> response = productService.addNewProduct(invalidProduct);

        assertEquals("INTERNAL_SERVER_ERROR", response.getStatus());
        assertTrue(response.getMessage().contains("Error adding product") || response.getMessage().contains("Catalog not found"));
        verify(productRepo, never()).save(any());
    }

    @Test
    void fetchProductByCatalog_success() {
        when(catalogRepo.findByCatalogName("Mobiles")).thenReturn(Optional.of(catalog));
        when(productRepo.findByCatalogId(100L)).thenReturn(List.of(product));

        EShopResponse<List<ProductDetails>> response = productService.fetchProductByCatalog("Mobiles");

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.FOUND.toString()));
        assertEquals(1, response.getData().size());
    }

    @Test
    void updateStocksForProductById_success() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any())).thenReturn(product);

        EShopResponse<ProductDetails> response = productService.updateStocksForProductById(1L, 10);

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.UPDATED.toString()));
        assertTrue(response.getData().getInStock());
    }

    @Test
    void deleteProductById_success() {
        when(productRepo.existsById(1L)).thenReturn(true);
        doNothing().when(productRepo).deleteById(1L);

        EShopResponse<String> response = productService.deleteProductById(1L);

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.DELETED.toString()));
        verify(productRepo).deleteById(1L);
    }

    @Test
    void deleteProductById_notFound() {
        when(productRepo.existsById(1L)).thenReturn(false);

        EShopResponse<String> response = productService.deleteProductById(1L);

        assertEquals("INTERNAL_SERVER_ERROR", response.getStatus());
        assertTrue(response.getMessage().contains("Product not found"));
        verify(productRepo, never()).deleteById(any());
    }

    @ParameterizedTest
    @MethodSource("stockUpdateProvider")
    void updateStocks_parameterized(int stock, boolean expectedInStock) {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(productRepo.save(any())).thenReturn(product);

        EShopResponse<ProductDetails> response = productService.updateStocksForProductById(1L, stock);

        assertEquals("OK", response.getStatus());
        assertTrue(response.getMessage().contains(AppEnums.UPDATED.toString()));
        assertEquals(expectedInStock, response.getData().getInStock());
    }

    private static Stream<Arguments> stockUpdateProvider() {
        return Stream.of(
                Arguments.of(10, true),
                Arguments.of(1, true),
                Arguments.of(0, false),
                Arguments.of(-5, false)
        );
    }
}