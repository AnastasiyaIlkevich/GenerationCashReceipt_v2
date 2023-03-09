package ru.clevertec.ilkevich.receipt.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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


//public class Product {
//
//    @Id
//    private Long id;
//    @NotBlank(message = "Name must not be empty")
//    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 .%-]*$", message = "Name must start with a letter and can only contain letters, numbers, %, . and -")
//    private String name;
//    @NotNull(message = "Price must not be empty")
//    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
//    private Double price;
//    @NotNull(message = "Promotional must not be empty")
//    private Boolean isPromotional;





@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ShopUpdateDto {
    @Min(1)
    private Long id;
    @NotBlank(message = "Name must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9 ,.!:-]*$", message = "The name must start with a letter and can contain any number of characters from 1 or more")
    private String shopName;
    @NotBlank(message = "Name must not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z -]{2,},[a-zA-Z][a-zA-Z0-9 ,:-]{2,}$", message = "The entry must correspond to the form -> city, address. " +
            "You can use only letters from 2 or more in the city entry. " +
            "Then the jammed (,) and the street name. You can use all letters and numbers and punctuation marks from 2 or more.")
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
