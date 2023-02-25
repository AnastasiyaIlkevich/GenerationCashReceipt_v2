package ru.clevertec.ilkevich.receipt.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ilkevich.receipt.dto.CashReceiptSaveDto;
import ru.clevertec.ilkevich.receipt.dto.CashReceiptUpdateDto;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

/**
 * Controller for entity CashReceipt (CRUD)
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/receipt")
public class CashReceiptController {

    private final AbstractService abstractService;

    @Autowired
    public CashReceiptController(@Qualifier("CashReceipt") AbstractService abstractService) {
        this.abstractService = abstractService;
    }

    @GetMapping
    private List<CashReceipt> getAllCashReceipt() {
        log.debug(CashReceiptController.class + ". Start method getAllCashReceipt");
        return abstractService.getAll();
    }

    @GetMapping("/{id}")
    public CashReceipt getCashReceiptById(@PathVariable("id") Long id) {
        log.info("Start method getCashReceiptById with id = " + id);
        return (CashReceipt) abstractService.findById(id);
    }

    @PostMapping()
    public void saveNewCashReceiptWithDefaultStore(@RequestBody CashReceiptSaveDto cashReceiptDto) {
        CashReceipt cashReceipt = cashReceiptDto.toCashReceipt();
        log.info("Start method saveNewCashReceiptWithDefaultStore " + cashReceipt);
        abstractService.save(cashReceipt);
    }

    @PostMapping("/shop/{id}")
    public void saveNewCashReceiptWithStoreId(@PathVariable("id") Long id,
                                              @RequestBody CashReceiptSaveDto cashReceiptDto) {
        CashReceipt cashReceipt = cashReceiptDto.toCashReceipt();
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(id);
        cashReceipt.setShopInfo(shopInfo);
        log.info(String.format("Start method saveNewCashReceiptWithStoreId id shop = %d, " +
                "cashReceipt = %s ", id, cashReceipt));
        abstractService.save(cashReceipt);
    }

    @PutMapping("/{id}")
    public CashReceiptUpdateDto updateCashReceipt(@PathVariable("id") Long id,
                                                  @RequestBody CashReceiptUpdateDto receiptUpdateDto) {
        receiptUpdateDto.setCheckNumber(id);
        CashReceipt cashReceipt = receiptUpdateDto.toCashReceipt();
        log.info(String.format("Start method updateCashReceipt with id = %d " +
                "and cashReceipt = %s", id, cashReceipt));
        return receiptUpdateDto.fromCashReceipt((CashReceipt) abstractService.update(cashReceipt));
    }

    @DeleteMapping("/{id}")
    public void deleteCashReceiptById(@PathVariable("id") Long id) {
        log.info("Start method deleteCashReceiptById with id = " + id);
        abstractService.deleteById(id);
    }
}
