package ru.clevertec.ilkevich.receipt.utils.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ilkevich.receipt.utils.TestBuilder;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;
import ru.clevertec.ilkevich.receipt.model.Product;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aCashReceipt")
public class CashReceiptTestBuilder implements TestBuilder<CashReceipt> {

    private Long checkNumber = 1L;
    private Timestamp dateCreation = new Timestamp(Instant.now().toEpochMilli());
    private Set<Product> setProduct = new HashSet<>();
    private ShopInfo shopInfo = ShopTestBuilder.aShopInfo().build();
    private DiscountCard discountCard = DiscountCardTestBuilder.aDiscountCard().build();


    public CashReceipt build() {
        setProduct.add(ProductTestBuilder.aProduct().build());
        final var receipt = new CashReceipt();
        receipt.setCheckNumber(checkNumber);
        receipt.setDateCreation(dateCreation);
        receipt.setSetProduct(setProduct);
        receipt.setShopInfo(shopInfo);
        receipt.setDiscountCard(discountCard);
        return receipt;


    }
}
