package ru.clevertec.ilkevich.receipt.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;

/**
 * Implementation of a data transfer object designed
 * to convert json into a DiscountCard object and vice versa.
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DiscountCardUpdateDto {
    private Long id;
    private Long cardNumber;
    private byte discount;

    public DiscountCard toDiscountCard() {
        DiscountCard discountCard = new DiscountCard();
        discountCard.setId(this.id);
        discountCard.setCardNumber(this.cardNumber);
        discountCard.setDiscount(this.discount);
        return discountCard;
    }

    public DiscountCardUpdateDto fromDiscountCard(DiscountCard discountCard) {
        DiscountCardUpdateDto discountCardDto = new DiscountCardUpdateDto();
        discountCardDto.setId(discountCard.getId());
        discountCardDto.setCardNumber(discountCard.getCardNumber());
        discountCardDto.setDiscount(discountCard.getDiscount());
        return discountCardDto;
    }
}