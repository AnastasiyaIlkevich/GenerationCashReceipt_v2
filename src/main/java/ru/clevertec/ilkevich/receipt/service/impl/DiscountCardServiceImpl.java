package ru.clevertec.ilkevich.receipt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;
import ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

/**
 * Implementation of the logic of the entity DiscountCard
 * for working with the database
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Component("DiscountCard")
public class DiscountCardServiceImpl implements AbstractService<DiscountCard, Long> {

    private final DiscountCardRepository discountCardRepository;

    @Autowired
    public DiscountCardServiceImpl(DiscountCardRepository discountCardRepository) {
        this.discountCardRepository = discountCardRepository;
    }

    @Override
    public List<DiscountCard> getAll() {
        return discountCardRepository.findAll();
    }

    @Override
    public DiscountCard findById(Long id) {
        return discountCardRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    //TODO - develop a logic for generating IDs with 100_000
    @Override
    public void save(DiscountCard discountCard) {
        discountCardRepository.save(discountCard);
    }

    @Override
    public DiscountCard update(DiscountCard discountCard) {
        discountCardRepository.findById(discountCard.getId()).orElseThrow(IdNotFoundException::new);
        return discountCardRepository.save(discountCard);
    }

    @Override
    public void deleteById(Long id) {
        discountCardRepository.deleteById(id);
    }
}
