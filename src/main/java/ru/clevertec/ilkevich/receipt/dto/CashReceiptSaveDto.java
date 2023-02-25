package ru.clevertec.ilkevich.receipt.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.Product;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a data transfer object designed
 * to convert json into a CashReceipt object and vice versa.
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CashReceiptSaveDto {

    private Set<ProductUpdateDto> setProduct;
    private DiscountCardUpdateDto discountCard;


    public CashReceipt toCashReceipt() {
        CashReceipt cashReceipt = new CashReceipt();
        Set<Product> products = new HashSet<>();
        for (ProductUpdateDto updateDto : setProduct) {
            products.add(updateDto.toProduct());
        }
        cashReceipt.setSetProduct(products);
        cashReceipt.setDiscountCard(discountCard.toDiscountCard());
        return cashReceipt;
    }


}
