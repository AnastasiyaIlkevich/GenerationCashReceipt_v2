package ru.clevertec.ilkevich.receipt.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.Product;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.repository.CashReceiptRepository;
import ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository;
import ru.clevertec.ilkevich.receipt.repository.ProductRepository;
import ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository;
import ru.clevertec.ilkevich.receipt.service.AbstractService;
import ru.clevertec.ilkevich.receipt.service.PDF.PdfGenerationService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the logic of the entity CashReceipt
 * for working with the database
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Log4j2
@Component("CashReceipt")
public class CashReceiptServiceImpl implements AbstractService<CashReceipt, Long> {

    private static final Long DEFAULT_SHOP = 6L;

    private final CashReceiptRepository cashReceiptRepository;
    private final ShopInfoRepository shopInfoRepository;
    private final PdfGenerationService pdfGenerationService;
    private final DiscountCardRepository discountCardRepository;
    private final ProductRepository productRepository;

    public CashReceiptServiceImpl(CashReceiptRepository cashReceiptRepository,
                                  ShopInfoRepository shopInfoRepository,
                                  PdfGenerationService pdfGenerationService,
                                  DiscountCardRepository discountCardRepository,
                                  ProductRepository productRepository) {
        this.cashReceiptRepository = cashReceiptRepository;
        this.shopInfoRepository = shopInfoRepository;
        this.pdfGenerationService = pdfGenerationService;
        this.discountCardRepository = discountCardRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CashReceipt> getAll() {
        return cashReceiptRepository.findAll();
    }

    @Override
    public CashReceipt findById(Long id) {
        return cashReceiptRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    @Override
    public void save(CashReceipt cashReceipt) {
        ShopInfo shopInfo;
        if (cashReceipt.getShopInfo() == null) {
            shopInfo = shopInfoRepository.findById(DEFAULT_SHOP).orElseThrow(IdNotFoundException::new);
        } else {
            shopInfo = shopInfoRepository.findById(cashReceipt.getShopInfo().getId())
                    .orElseThrow(IdNotFoundException::new);
        }
        cashReceipt.setShopInfo(shopInfo);
        cashReceipt.setDateCreation(new Timestamp(Instant.now().toEpochMilli()));
        cashReceiptRepository.save(cashReceipt);
        log.debug("Save new Object");

        cashReceipt = cashReceiptRepository.findByDateCreation(cashReceipt.getDateCreation());
        cashReceipt.setDiscountCard(discountCardRepository.findById(cashReceipt.getDiscountCard().getId()).orElseThrow(IdNotFoundException::new));

        List<Product> allProductById = productRepository.findAllById(cashReceipt.getSetProduct()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet()));
        cashReceipt.setSetProduct(new HashSet<>(allProductById));
        pdfGenerationService.creatingPdfFile(cashReceipt);
    }


    @Override
    public CashReceipt update(CashReceipt cashReceipt) {
        cashReceiptRepository.findById(cashReceipt.getCheckNumber()).orElseThrow(IdNotFoundException::new);
        log.debug("update Object");
        return cashReceiptRepository.save(cashReceipt);
    }

    @Override
    public void deleteById(Long id) {
        cashReceiptRepository.deleteById(id);
        log.debug("delete Object");
    }
}
