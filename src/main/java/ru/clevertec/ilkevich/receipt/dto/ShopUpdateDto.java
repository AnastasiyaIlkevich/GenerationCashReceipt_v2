package ru.clevertec.ilkevich.receipt.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;

/**
 * Implementation of a data transfer object designed
 * to convert json into a ShopInfo object and vice versa.
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ShopUpdateDto {

    private Long id;
    private String shopName;
    private String address;
    private String phoneNumber;

    public ShopInfo toShopInfo() {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(this.id);
        shopInfo.setShopName(this.shopName);
        shopInfo.setAddress(this.address);
        shopInfo.setPhoneNumber(this.phoneNumber);
        return shopInfo;
    }

    public ShopUpdateDto fromShopInfo(ShopInfo shopInfo) {
        ShopUpdateDto shopDto = new ShopUpdateDto();
        shopDto.setId(shopInfo.getId());
        shopDto.setShopName(shopInfo.getShopName());
        shopDto.setAddress(shopInfo.getAddress());
        shopDto.setPhoneNumber(shopInfo.getPhoneNumber());
        return shopDto;
    }
}
