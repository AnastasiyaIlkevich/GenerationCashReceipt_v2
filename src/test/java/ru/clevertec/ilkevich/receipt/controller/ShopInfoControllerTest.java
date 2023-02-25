package ru.clevertec.ilkevich.receipt.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ilkevich.receipt.utils.impl.ShopTestBuilder;
import ru.clevertec.ilkevich.receipt.dto.ShopUpdateDto;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ShopInfoControllerTest {

    @Mock
    private AbstractService abstractService;
    @Mock
    private static ShopUpdateDto shopUpdateDto;
    @InjectMocks
    private ShopInfoController shopInfoController;

    private static ShopInfo shopInfo;

    @BeforeAll
    static void setUp() {
        shopInfo = ShopTestBuilder.aShopInfo().build();
    }

    @Test
    void getAllShopInfoById() {
        List<ShopInfo> shopInfoList = List.of(
                shopInfo,
                ShopTestBuilder.aShopInfo()
                        .withId(2L)
                        .withShopName("test name")
                        .withAddress("test address")
                        .withPhoneNumber("test phoneNumber")
                        .build());
        when(abstractService.getAll()).thenReturn(shopInfoList);
        List<ShopInfo> resultShopList = abstractService.getAll();
        assertEquals(2, resultShopList.size());
    }

    @Test
    void saveShopInfo() {
        when(shopUpdateDto.toShopInfo()).thenReturn(shopInfo);
        ShopInfo shopInfo = shopUpdateDto.toShopInfo();
        abstractService.save(shopInfo);
        verify(abstractService).save(shopInfo);
    }
}
