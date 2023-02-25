package ru.clevertec.ilkevich.receipt.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private List<DiscountCard> getAllDiscountCard() {
        log.debug(DiscountCardController.class + ". Start method getAllDiscountCard");
        return abstractService.getAll();
    }

    @GetMapping("/{id}")
    public DiscountCard getDiscountCardById(@PathVariable("id") Long id) {
        log.info("Start method getDiscountCardById with id = " + id);
        return (DiscountCard) abstractService.findById(id);
    }

    @PostMapping()
    public void saveDiscountCard(@RequestBody DiscountCardUpdateDto discountCardDto) {
        DiscountCard discountCard = discountCardDto.toDiscountCard();
        log.info("Start method saveDiscountCard " + discountCard);
        abstractService.save(discountCard);
    }

    @PutMapping("/{id}")
    public DiscountCardUpdateDto updateDiscountCard(@PathVariable("id") Long id,
                                                    @RequestBody DiscountCardUpdateDto discountCardDto) {
        discountCardDto.setId(id);
        DiscountCard discountCard = discountCardDto.toDiscountCard();
        log.info(String.format("Start method updateDiscountCard with id = %d " +
                "and discountCard = %s", id, discountCard));
        return discountCardDto.fromDiscountCard((DiscountCard) abstractService.update(discountCard));
    }

    @DeleteMapping("/{id}")
    public void deleteDiscountCardById(@PathVariable("id") Long id) {
        log.info("Start method deleteDiscountCardById with id = " + id);
        abstractService.deleteById(id);
    }

}
