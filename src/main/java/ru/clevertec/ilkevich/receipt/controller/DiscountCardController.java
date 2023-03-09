package ru.clevertec.ilkevich.receipt.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ilkevich.receipt.dto.DiscountCardUpdateDto;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

/**
 * Controller for entity DiscountCard (CRUD)
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/discount")
public class DiscountCardController {

    private final AbstractService abstractService;

    @Autowired
    public DiscountCardController(@Qualifier("DiscountCard") AbstractService abstractService) {
        this.abstractService = abstractService;
    }

    @GetMapping
    private ResponseEntity<List<DiscountCard>> getAllDiscountCard() {
        log.debug(DiscountCardController.class + ". Start method getAllDiscountCard");
        List<DiscountCard> discountCardList = abstractService.getAll();
        return ResponseEntity.ok(discountCardList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCard> getDiscountCardById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method getDiscountCardById with id = " + id);
        DiscountCard discountCard = (DiscountCard) abstractService.findById(id);
        return ResponseEntity.ok(discountCard);
    }

    @PostMapping()
    public ResponseEntity<?> saveDiscountCard(@Valid @RequestBody DiscountCardUpdateDto discountCardDto) {
        DiscountCard discountCard = discountCardDto.toDiscountCard();
        log.info("Start method saveDiscountCard " + discountCard);
        abstractService.save(discountCard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCardUpdateDto> updateDiscountCard(@PathVariable("id") @Min(1) Long id,
                                                                    @Valid @RequestBody DiscountCardUpdateDto discountCardDto) {
        discountCardDto.setId(id);
        DiscountCard discountCard = discountCardDto.toDiscountCard();
        log.info(String.format("Start method updateDiscountCard with id = %d " +
                "and discountCard = %s", id, discountCard));
        discountCardDto = discountCardDto.fromDiscountCard((DiscountCard) abstractService.update(discountCard));
        return ResponseEntity.ok(discountCardDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscountCardById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method deleteDiscountCardById with id = " + id);
        abstractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
