package ru.clevertec.ilkevich.receipt.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ilkevich.receipt.dto.ProductUpdateDto;
import ru.clevertec.ilkevich.receipt.model.Product;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

/**
 * Controller for entity Product (CRUD)
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/product")
public class ProductController {

    private final AbstractService abstractService;

    @Autowired
    public ProductController(@Qualifier("ProductService") AbstractService abstractService) {
        this.abstractService = abstractService;
    }

    @GetMapping
    private List<Product> getAllProduct() {
        log.debug(ProductController.class + ". Start method getAllProduct");
        return abstractService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        log.info("Start method getProductById with id = " + id);
        return (Product) abstractService.findById(id);
    }

    @PostMapping()
    public void saveProduct(@RequestBody ProductUpdateDto productDto) {
        Product product = productDto.toProduct();
        log.info(String.format("Start method saveProduct name = %s, " +
                "price = %s ", product.getName(), product.getPrice()));
        abstractService.save(product);
    }

    @PutMapping("/{id}")
    public ProductUpdateDto updateProduct(@PathVariable("id") Long id, @RequestBody ProductUpdateDto productDto) {
        productDto.setId(id);
        Product product = productDto.toProduct();
        log.info(String.format("Start method updateProduct with id = %d " +
                "and product = %s", id, product));
        return productDto.fromProduct((Product) abstractService.update(product));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        log.info("Start method deleteProductById with id = " + id);
        abstractService.deleteById(id);
    }
}
