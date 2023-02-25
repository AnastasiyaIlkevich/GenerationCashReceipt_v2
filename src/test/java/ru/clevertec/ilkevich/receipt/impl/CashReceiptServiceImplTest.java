package ru.clevertec.ilkevich.receipt.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ilkevich.receipt.utils.impl.CashReceiptTestBuilder;
import ru.clevertec.ilkevich.receipt.utils.impl.ShopTestBuilder;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.repository.CashReceiptRepository;
import ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository;
import ru.clevertec.ilkevich.receipt.service.impl.CashReceiptServiceImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashReceiptServiceImplTest {

    @Mock
    private CashReceiptRepository cashReceiptRepository;
    @Mock
    private ShopInfoRepository shopInfoRepository;
    @InjectMocks
    private CashReceiptServiceImpl cashReceiptService;

    private static CashReceipt cashReceipt;
    private static ShopInfo shopInfo;

    @BeforeEach
    void setUp() {
        cashReceipt = CashReceiptTestBuilder.aCashReceipt().build();
        shopInfo = ShopTestBuilder.aShopInfo().build();
    }

    @Test
    @DisplayName("Find all CashReceipt objects")
    void checkGetAllShouldReturnSize2() {
        List<CashReceipt> cashReceiptList = List.of(
                cashReceipt,
                CashReceiptTestBuilder.aCashReceipt()
                        .withCheckNumber(2222L)
                        .withDateCreation(new Timestamp(Instant.now().toEpochMilli()))
                        .build()
        );
        when(cashReceiptRepository.findAll()).thenReturn(cashReceiptList);
        List<CashReceipt> resultReceiptList = cashReceiptService.getAll();
        assertEquals(2, resultReceiptList.size());

    }

    @Test
    @DisplayName("Find all CashReceipt objects - not found")
    void checkGetAllShouldReturnException() {
        when(cashReceiptRepository.findAll()).thenReturn(new ArrayList<>());
        List<CashReceipt> resultCashReceiptList = cashReceiptService.getAll();
        assertThrows(IndexOutOfBoundsException.class, () -> resultCashReceiptList.get(0));
    }

    @Test
    @DisplayName("Find entity CashReceipt by ID")
    void checkFindByIdShouldEqualEntity() {
        when(cashReceiptRepository.findById(cashReceipt.getCheckNumber())).thenReturn(ofNullable(cashReceipt));
        CashReceipt result = cashReceiptService.findById(cashReceipt.getCheckNumber());
        assertEquals(cashReceipt, result);
    }

    @Test
    @DisplayName("Find entity CashReceipt by ID - not found")
    void checkFindByIdShouldReturnException() {
        when(cashReceiptRepository.findById(cashReceipt.getCheckNumber())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> cashReceiptService.findById(cashReceipt.getCheckNumber()));
        verify(cashReceiptRepository, times(1)).findById(cashReceipt.getCheckNumber());
    }

    @Test
    @DisplayName("Save entity CashReceipt")
    void checkSaveShouldReturnVerifyOnce() {
        when(shopInfoRepository.findById(cashReceipt.getShopInfo().getId())).thenReturn(ofNullable(shopInfo));
        cashReceiptService.save(cashReceipt);
        verify(cashReceiptRepository).save(cashReceipt);
    }

    @Test
    @DisplayName("Save entity CashReceipt - DEFAULT_SHOP")
    void checkSaveShouldReturnDefaultShop() {
        cashReceipt.setShopInfo(null);
        if (cashReceipt.getShopInfo() == null) {
            when(shopInfoRepository.findById(1L)).thenReturn(ofNullable(ShopTestBuilder.aShopInfo()
                    .withShopName("DEFAULT_SHOP")
                    .build()));
        }
        cashReceiptService.save(cashReceipt);
        verify(cashReceiptRepository).save(cashReceipt);
    }

    @Test
    @DisplayName("Update entity CashReceipt by ID - not found")
    void checkUpdateShouldReturnException() {
        when(cashReceiptRepository.findById(cashReceipt.getCheckNumber())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> cashReceiptService.update(cashReceipt));
        verify(cashReceiptRepository, times(1)).findById(cashReceipt.getCheckNumber());
        verify(cashReceiptRepository, never()).save(cashReceipt);
    }

    @Test
    @DisplayName("Update entity CashReceipt by ID")
    void checkUpdateShouldEqualEntity() {
        when(cashReceiptRepository.findById(cashReceipt.getCheckNumber())).thenReturn(ofNullable(cashReceipt));
        when(cashReceiptRepository.save(cashReceipt)).thenReturn(cashReceipt);
        CashReceipt update = cashReceiptService.update(cashReceipt);
        verify(cashReceiptRepository, times(1)).findById(cashReceipt.getCheckNumber());
        verify(cashReceiptRepository, times(1)).save(cashReceipt);
        assertEquals(cashReceipt, update);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 9L})
    @DisplayName("Delete CashReceipt by ID - ParameterizedTest")
    void checkDeleteByIdShouldReturnVerifyOnce(Long id) {
        cashReceiptService.deleteById(id);
        verify(cashReceiptRepository, times(1)).deleteById(id);
    }
}
