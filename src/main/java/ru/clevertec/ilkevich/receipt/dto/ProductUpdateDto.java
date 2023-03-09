package ru.clevertec.ilkevich.receipt.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import ru.clevertec.ilkevich.receipt.model.Product;

/**
 * Implementation of a data transfer object designed
 * to convert json into a Product object and vice versa.
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ProductUpdateDto {
    @Min(1)
    private Long id;
    @NotBlank(message = "Name must not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9 ,.!:%-]{3,}$", message = "The name must start with a letter and can contain any number of characters from 3 or more")
    private String name;
    @NotBlank(message = "Name must not be empty")
    @DecimalMin(value = "0.01", message = "The price should be from 0.01 or more")
    private Double price;

    public Product toProduct() {

        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setPrice(this.price);
        return product;
    }

    public ProductUpdateDto fromProduct (Product product) {
        ProductUpdateDto productDto = new ProductUpdateDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
