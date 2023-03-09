package ru.clevertec.ilkevich.receipt.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<List<Product>> getAllProduct() {
        log.debug(ProductController.class + ". Start method getAllProduct");
        List<Product> productList = abstractService.getAll();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method getProductById with id = " + id);
        Product product = (Product) abstractService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductUpdateDto productDto) {
        Product product = productDto.toProduct();
        log.info(String.format("Start method saveProduct name = %s, " +
                "price = %s ", product.getName(), product.getPrice()));
        abstractService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateDto> updateProduct(@PathVariable("id") @Min(1) Long id,
                                                          @Valid @RequestBody ProductUpdateDto productDto) {
        productDto.setId(id);
        Product product = productDto.toProduct();
        log.info(String.format("Start method updateProduct with id = %d " +
                "and product = %s", id, product));
        productDto = productDto.fromProduct((Product) abstractService.update(product));
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method deleteProductById with id = " + id);
        abstractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
