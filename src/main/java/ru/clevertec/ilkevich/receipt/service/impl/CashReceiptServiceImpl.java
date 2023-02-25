package ru.clevertec.ilkevich.receipt.service.impl;


import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.repository.CashReceiptRepository;
import ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Implementation of the logic of the entity CashReceipt
 * for working with the database
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Component("CashReceipt")
public class CashReceiptServiceImpl implements AbstractService<CashReceipt, Long> {

    private static final Long DEFAULT_SHOP = 1L;

    private final CashReceiptRepository cashReceiptRepository;
    private final ShopInfoRepository shopInfoRepository;


    public CashReceiptServiceImpl(CashReceiptRepository cashReceiptRepository, ShopInfoRepository shopInfoRepository) {
        this.cashReceiptRepository = cashReceiptRepository;
        this.shopInfoRepository = shopInfoRepository;
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


        if (cashReceipt.getShopInfo() == null) {
            ShopInfo shopInfo = shopInfoRepository.findById(DEFAULT_SHOP).orElseThrow(IdNotFoundException::new);
            cashReceipt.setShopInfo(shopInfo);
        } else {
            ShopInfo shopInfo = shopInfoRepository.findById(cashReceipt.getShopInfo().getId())
                    .orElseThrow(IdNotFoundException::new);
            cashReceipt.setShopInfo(shopInfo);
        }
        cashReceipt.setDateCreation(new Timestamp(Instant.now().toEpochMilli()));
        cashReceiptRepository.save(cashReceipt);

//        generateRecaiptFile.createFile(cashReceiptRepository.findByDateCreation(cashReceipt.getDateCreation())
//                .getCheckNumber());
//        generateRecaiptFile.writeFile();
    }


    @Override
    public CashReceipt update(CashReceipt cashReceipt) {
        cashReceiptRepository.findById(cashReceipt.getCheckNumber()).orElseThrow(IdNotFoundException::new);
        return cashReceiptRepository.save(cashReceipt);
    }

    @Override
    public void deleteById(Long id) {
        cashReceiptRepository.deleteById(id);

    }
}
