package ru.clevertec.ilkevich.receipt.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ilkevich.receipt.utils.impl.ShopTestBuilder;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopUpdateDtoTest {

    @Mock
    private ShopUpdateDto shopUpdateDto;

    private static ShopInfo shopInfo;

    @BeforeAll
    static void setUp() {
        shopInfo = ShopTestBuilder.aShopInfo().build();
   }
//checkGetAllShouldReturnSize2()
    //    @DisplayName("Find all CashReceipt objects")
    @Test
    @DisplayName("DTO object conversion to object ")
    void checkConversionToShopInfoShouldEquals() {
        when(shopUpdateDto.toShopInfo()).thenReturn(shopInfo);
        ShopInfo result = shopUpdateDto.toShopInfo();
        assertEquals(shopInfo, result);
        verify(shopUpdateDto, times(1)).toShopInfo();
    }

    @Test
    @DisplayName("Object conversion to DTO object")
    void checkConversionFromShopInfoShouldEquals() {
        when(shopUpdateDto.fromShopInfo(shopInfo)).thenReturn(shopUpdateDto);
        ShopUpdateDto result = shopUpdateDto.fromShopInfo(shopInfo);
        assertEquals(shopUpdateDto, result);
        verify(shopUpdateDto, times(1)).fromShopInfo(shopInfo);
    }
}