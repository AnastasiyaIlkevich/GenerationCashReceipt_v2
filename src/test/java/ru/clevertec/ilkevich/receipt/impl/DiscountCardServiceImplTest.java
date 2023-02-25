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
import ru.clevertec.ilkevich.receipt.utils.impl.DiscountCardTestBuilder;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;
import ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository;
import ru.clevertec.ilkevich.receipt.service.impl.DiscountCardServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceImplTest {

    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private DiscountCardServiceImpl discountCardService;

    private static DiscountCard discountCard;

    @BeforeAll
    static void setUp() {
        discountCard = DiscountCardTestBuilder.aDiscountCard().build();
    }

    @Test
    @DisplayName("Find all DiscountCard objects")
    void checkGetAllShouldReturnSize2() {
        List<DiscountCard> discountCardList = List.of(
                discountCard,
                DiscountCardTestBuilder.aDiscountCard()
                        .withDiscount((byte) 20)
                        .withId(2L)
                        .withCardNumber(2222L)
                        .build()
        );
        when(discountCardRepository.findAll()).thenReturn(discountCardList);
        List<DiscountCard> resultDiscountList = discountCardService.getAll();
        assertEquals(2, resultDiscountList.size());
    }

    @Test
    @DisplayName("Find all DiscountCard objects - not found")
    void checkGetAllShouldReturnException() {
        when(discountCardRepository.findAll()).thenReturn(new ArrayList<>());
        List<DiscountCard> resultProductList = discountCardService.getAll();
        assertThrows(IndexOutOfBoundsException.class, () -> resultProductList.get(0));
    }

    @Test
    @DisplayName("Find entity DiscountCard by ID")
    void checkFindByIdShouldEqualEntity() {
        when(discountCardRepository.findById(discountCard.getId())).thenReturn(ofNullable(discountCard));
        DiscountCard result = discountCardService.findById(discountCard.getId());
        assertEquals(discountCard, result);
    }

    @Test
    @DisplayName("Find entity DiscountCard by ID - not found")
    void checkFindByIdShouldReturnException() {
        when(discountCardRepository.findById(discountCard.getId())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> discountCardService.findById(discountCard.getId()));
        verify(discountCardRepository, times(1)).findById(discountCard.getId());
    }

    @Test
    @DisplayName("Save entity DiscountCard")
    void checkSaveShouldReturnVerifyOnce() {
        discountCardService.save(discountCard);
        verify(discountCardRepository).save(discountCard);
    }

    @Test
    @DisplayName("Update entity DiscountCard by ID - not found")
    void checkUpdateShouldReturnException() {
        when(discountCardRepository.findById(discountCard.getId())).thenThrow(IdNotFoundException.class);
        assertThrows(IdNotFoundException.class, () -> discountCardService.update(discountCard));
        verify(discountCardRepository, times(1)).findById(discountCard.getId());
        verify(discountCardRepository, never()).save(discountCard);
    }

    @Test
    @DisplayName("Update entity DiscountCard by ID")
    void checkUpdateShouldEqualEntity() {
        when(discountCardRepository.findById(discountCard.getId())).thenReturn(ofNullable(discountCard));
        when(discountCardRepository.save(discountCard)).thenReturn(discountCard);
        DiscountCard update = discountCardService.update(discountCard);
        verify(discountCardRepository, times(1)).findById(discountCard.getId());
        verify(discountCardRepository, times(1)).save(discountCard);
        assertEquals(discountCard, update);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 9L})
    @DisplayName("Delete DiscountCard by ID - ParameterizedTest")
    void checkDeleteByIdShouldReturnVerifyOnce(Long id) {
        discountCardService.deleteById(id);
        verify(discountCardRepository, times(1)).deleteById(id);
    }
}
