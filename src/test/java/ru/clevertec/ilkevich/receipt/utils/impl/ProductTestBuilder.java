package ru.clevertec.ilkevich.receipt.utils.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ilkevich.receipt.utils.TestBuilder;
import ru.clevertec.ilkevich.receipt.model.Product;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aProduct")
public class ProductTestBuilder implements TestBuilder<Product> {

    private Long id = 1L;
    private String name = "test";
    private Double price = 1.0;

    @Override
    public Product build() {
        final var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
