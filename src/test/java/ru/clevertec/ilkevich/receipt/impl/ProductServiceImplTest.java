package ru.clevertec.ilkevich.receipt.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ilkevich.receipt.utils.impl.ProductTestBuilder;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.Product;
import ru.clevertec.ilkevich.receipt.repository.ProductRepository;
import ru.clevertec.ilkevich.receipt.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private static Product product;

    @BeforeAll
    static void setUp() {
        product = ProductTestBuilder.aProduct().build();
    }

    @Test
    @DisplayName("Find all Product objects")
    void checkGetAllShouldReturnSize2() {
        List<Product> productList = List.of(
                product,
                ProductTestBuilder.aProduct()
                        .withId(2L)
                        .withName("testProduct 2")
                        .withPrice(2.2)
                        .build());
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> resultProductList = productService.getAll();
        assertEquals(2, resultProductList.size());
    }

    @Test
    @DisplayName("Find all Product objects - not found")
    void checkGetAllShouldReturnException() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        List<Product> resultProductList = productService.getAll();
        assertThrows(IndexOutOfBoundsException.class, () -> resultProductList.get(0));
    }

    @Test
    @DisplayName("Find entity Product by ID")
    void checkFindByIdShouldEqualEntity() {
        when(productRepository.findById(product.getId())).thenReturn(ofNullable(product));
        Product result = productService.findById(product.getId());
        assertEquals(product, result);
    }

    @Test
    @DisplayName("Find entity Product by ID - not found")
    void checkFindByIdShouldReturnException() {
        when(productRepository.findById(product.getId())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> productService.findById(product.getId()));
        verify(productRepository, times(1)).findById(product.getId());
    }


    @Test
    @DisplayName("Save entity Product")
    void checkSaveShouldReturnVerifyOnce() {
        productService.save(product);
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Update entity Product by ID - not found")
    void checkUpdateShouldReturnException() {
        when(productRepository.findById(product.getId())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> productService.update(product));
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, never()).save(product);
    }

    @Test
    @DisplayName("Update entity Product by ID")
    void checkUpdateShouldEqualEntity() {
        when(productRepository.findById(product.getId())).thenReturn(ofNullable(product));
        when(productRepository.save(product)).thenReturn(product);
        Product update = productService.update(product);
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(product);
        assertEquals(product, update);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 9L})
    @DisplayName("Delete Product by ID - ParameterizedTest")
    void checkDeleteByIdShouldReturnVerifyOnce(Long id) {
        productService.deleteById(id);
        verify(productRepository, times(1)).deleteById(id);
    }
}
