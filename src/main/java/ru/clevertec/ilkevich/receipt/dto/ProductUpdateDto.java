package ru.clevertec.ilkevich.receipt.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    private Long id;
    private String name;
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
