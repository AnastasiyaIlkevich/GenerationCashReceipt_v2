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
public class CashReceiptUpdateDto extends CashReceiptSaveDto {
    private Long checkNumber;
    private ShopUpdateDto shopUpdateDto;

    @Override
    public CashReceipt toCashReceipt() {

        CashReceipt cashReceipt = super.toCashReceipt();
        cashReceipt.setCheckNumber(this.checkNumber);
        cashReceipt.setShopInfo(shopUpdateDto.toShopInfo());
        return cashReceipt;
    }

    public CashReceiptUpdateDto fromCashReceipt(CashReceipt cashReceipt) {
        CashReceiptUpdateDto receiptUpdateDto = new CashReceiptUpdateDto();
        ProductUpdateDto productUpdateDto = new ProductUpdateDto();
        Set<ProductUpdateDto> productUpdateDtoSet = new HashSet<>();

        receiptUpdateDto.setCheckNumber(cashReceipt.getCheckNumber());
        for (Product product : cashReceipt.getSetProduct()) {
            productUpdateDtoSet.add(productUpdateDto.fromProduct(product));
        }
        receiptUpdateDto.setSetProduct(productUpdateDtoSet);

        receiptUpdateDto.setDiscountCard(new DiscountCardUpdateDto().fromDiscountCard(cashReceipt.getDiscountCard()));
        receiptUpdateDto.setShopUpdateDto(new ShopUpdateDto().fromShopInfo(cashReceipt.getShopInfo()));
        return receiptUpdateDto;
    }
}
