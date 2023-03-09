package ru.clevertec.ilkevich.receipt.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Min(1)
    private Long id;
    @NotBlank(message = "Name must not be empty")
    @Min(value = 1000000, message = "The card number must be greater than or equal to 1000000")
    @Max(value = 9999999, message = "The card number must be less than or equal to 9999999")
    private Long cardNumber;
    @Min(value = 3, message = "The card number must be greater than or equal to 3")
    @Max(value = 99, message = "The card number must be less than or equal to 99")
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