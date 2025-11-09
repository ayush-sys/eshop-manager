package com.example.eshop.service;

import org.springframework.boot.test.context.SpringBootTest;
import com.example.eshop.dao.model.ProductDetails;
import com.example.eshop.dao.repository.IProductCatalogRepository;
import com.example.eshop.dao.repository.IProductDetailsRepository;
import com.example.eshop.enums.AppEnums;
import com.example.eshop.utils.EShopResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private IProductDetailsRepository productRepo;

    @Mock
    private IProductCatalogRepository catalogRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDetails product;

    @BeforeEach
    void setUp() {
        product = new ProductDetails();
        product.setProductName("iPhone 15");
        product.setCatalogName("Mobiles");
        product.setPrice(BigDecimal.valueOf(120000.00));
    }

    @Test
    void testFetchAllProducts_Success() {
        // Arrange
        when(productRepo.findAll()).thenReturn(List.of(product));

        // Act
        EShopResponse<List<ProductDetails>> response = productService.fetchAllProducts();

        // Assert
        assertNotNull(response);
        assertEquals("OK", response.getStatus());
        assertEquals(AppEnums.FOUND.toString(), response.getMessage());
        assertEquals(1, response.getData().size());
        assertEquals("iPhone 15", response.getData().get(0).getProductName());

        verify(productRepo, times(1)).findAll();
    }

}
