package ru.clevertec.ilkevich.receipt.utils.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ilkevich.receipt.utils.TestBuilder;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aDiscountCard")
public class DiscountCardTestBuilder implements TestBuilder<DiscountCard> {

    private Long id =1L;
    private Long cardNumber= 1234L;
    private byte discount = 10;

    @Override
    public DiscountCard build() {
        final var discountCard = new DiscountCard();
        discountCard.setId(id);
        discountCard.setCardNumber(cardNumber);
        discountCard.setDiscount(discount);
        return discountCard;
    }
}
