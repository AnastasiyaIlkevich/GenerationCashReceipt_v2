package ru.clevertec.ilkevich.receipt.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ilkevich.receipt.utils.impl.ProductTestBuilder;
import ru.clevertec.ilkevich.receipt.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUpdateDtoTest {

    @Mock
    private ProductUpdateDto productUpdateDto;

    private static Product product;

    @BeforeAll
    static void setUp() {
        product = ProductTestBuilder.aProduct().build();
    }

    @Test
    @DisplayName("DTO object conversion to object ")
    void checkConversionToProductShouldEquals() {
        when(productUpdateDto.toProduct()).thenReturn(product);
        Product result = productUpdateDto.toProduct();
        assertEquals(product, result);
        verify(productUpdateDto, times(1)).toProduct();
    }

    @Test
    @DisplayName("Object conversion to DTO object")
    void checkConversionFromProductShouldEquals() {
        when(productUpdateDto.fromProduct(product)).thenReturn(productUpdateDto);
        ProductUpdateDto result = productUpdateDto.fromProduct(product);
        assertEquals(productUpdateDto, result);
        verify(productUpdateDto, times(1)).fromProduct(product);
    }
}
