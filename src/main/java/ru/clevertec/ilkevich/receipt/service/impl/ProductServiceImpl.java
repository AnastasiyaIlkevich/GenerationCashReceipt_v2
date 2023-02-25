package ru.clevertec.ilkevich.receipt.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.Product;
import ru.clevertec.ilkevich.receipt.repository.ProductRepository;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

/**
 * Implementation of the logic of the entity Product
 * for working with the database
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */


@Component("ProductService")
public class ProductServiceImpl implements AbstractService<Product, Long> {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        productRepository.findById(product.getId()).orElseThrow(IdNotFoundException::new);
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}

