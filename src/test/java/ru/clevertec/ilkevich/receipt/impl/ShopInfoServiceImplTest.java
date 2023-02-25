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
import ru.clevertec.ilkevich.receipt.utils.impl.ShopTestBuilder;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository;
import ru.clevertec.ilkevich.receipt.service.impl.ShopInfoServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopInfoServiceImplTest {

    @Mock
    private ShopInfoRepository shopInfoRepository;
    @InjectMocks
    private ShopInfoServiceImpl shopInfoService;

    private static ShopInfo shopInfo;

    @BeforeAll
    static void setUp() {
        shopInfo = ShopTestBuilder.aShopInfo().build();
    }

    @Test
    @DisplayName("Find entity ShopInfo by ID")
    void checkFindByIdShouldEqualEntity() {
        when(shopInfoRepository.findById(shopInfo.getId())).thenReturn(ofNullable(shopInfo));
        ShopInfo result = shopInfoService.findById(shopInfo.getId());
        assertEquals(shopInfo, result);
    }

    @Test
    @DisplayName("Find entity ShopInfo by ID - not found")
    void checkFindByIdShouldReturnException() {
        when(shopInfoRepository.findById(shopInfo.getId())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> shopInfoService.findById(shopInfo.getId()));
        verify(shopInfoRepository, times(1)).findById(shopInfo.getId());
    }

    @Test
    @DisplayName("Find all ShopInfo objects")
    void checkGetAllShouldReturnSize2() {
        List<ShopInfo> shopInfoList = List.of(
                shopInfo,
                ShopTestBuilder.aShopInfo()
                        .withId(2L)
                        .withShopName("test name")
                        .withAddress("test address")
                        .withPhoneNumber("test phoneNumber")
                        .build());
        when(shopInfoRepository.findAll()).thenReturn(shopInfoList);
        List<ShopInfo> resultShopList = shopInfoService.getAll();
        assertEquals(2, resultShopList.size());
    }

    @Test
    @DisplayName("Find all ShopInfo objects - not found")
    void checkGetAllShouldReturnException() {
        when(shopInfoRepository.findAll()).thenReturn(new ArrayList<>());
        List<ShopInfo> resultShopList = shopInfoService.getAll();
        assertThrows(IndexOutOfBoundsException.class, () -> resultShopList.get(0));
    }

    @Test
    @DisplayName("Save entity ShopInfo")
    void checkSaveShouldReturnVerifyOnce() {
        shopInfoService.save(shopInfo);
        verify(shopInfoRepository).save(shopInfo);
    }

    @Test
    @DisplayName("Update entity ShopInfo by ID - not found")
    void checkUpdateShouldReturnException() {
        when(shopInfoRepository.findById(shopInfo.getId())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> shopInfoService.update(shopInfo));
        verify(shopInfoRepository, times(1)).findById(shopInfo.getId());
        verify(shopInfoRepository, never()).save(shopInfo);
    }

    @Test
    @DisplayName("Update entity ShopInfo by ID")
    void checkUpdateShouldEqualEntity() {
        when(shopInfoRepository.findById(shopInfo.getId())).thenReturn(ofNullable(shopInfo));
        when(shopInfoRepository.save(shopInfo)).thenReturn(shopInfo);
        ShopInfo update = shopInfoService.update(shopInfo);
        verify(shopInfoRepository, times(1)).findById(shopInfo.getId());
        verify(shopInfoRepository, times(1)).save(shopInfo);
        assertEquals(shopInfo, update);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 9L})
    @DisplayName("Delete ShopInfo by ID - ParameterizedTest")
    void checkDeleteByIdShouldReturnVerifyOnce(Long id) {
        shopInfoService.deleteById(id);
        verify(shopInfoRepository, times(1)).deleteById(id);
    }
}
