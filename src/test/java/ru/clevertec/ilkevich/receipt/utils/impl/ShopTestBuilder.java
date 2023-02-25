package ru.clevertec.ilkevich.receipt.utils.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ilkevich.receipt.utils.TestBuilder;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aShopInfo")
public class ShopTestBuilder implements TestBuilder<ShopInfo> {

    private Long id = 1L;
    private String shopName = "My Crown";
    private String address = "some city, some street, some house";
    private String phoneNumber = "+375 17 268 55 55";

    @Override
    public ShopInfo build() {
        final var shopInfo = new ShopInfo();
        shopInfo.setId(id);
        shopInfo.setShopName(shopName);
        shopInfo.setAddress(address);
        shopInfo.setPhoneNumber(phoneNumber);
        return shopInfo;
    }
}
