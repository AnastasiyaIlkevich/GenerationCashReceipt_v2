package ru.clevertec.ilkevich.receipt.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ilkevich.receipt.utils.impl.DiscountCardTestBuilder;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardUpdateDtoTest {

    @Mock
    private DiscountCardUpdateDto discountCardUpdateDto;

    private static DiscountCard discountCard;

    @BeforeAll
    static void setUp() {
        discountCard = DiscountCardTestBuilder.aDiscountCard().build();
    }

    @Test
    @DisplayName("DTO object conversion to object ")
    void checkConversionToDiscountCardShouldEquals() {
        when(discountCardUpdateDto.toDiscountCard()).thenReturn(discountCard);
        DiscountCard result = discountCardUpdateDto.toDiscountCard();
        assertEquals(discountCard, result);
        verify(discountCardUpdateDto, times(1)).toDiscountCard();
    }

    @Test
    @DisplayName("Object conversion to DTO object")
    void checkConversionFromDiscountCardShouldEquals() {
        when(discountCardUpdateDto.fromDiscountCard(discountCard)).thenReturn(discountCardUpdateDto);
        DiscountCardUpdateDto result = discountCardUpdateDto.fromDiscountCard(discountCard);
        assertEquals(discountCardUpdateDto, result);
        verify(discountCardUpdateDto, times(1)).fromDiscountCard(discountCard);
    }
}
