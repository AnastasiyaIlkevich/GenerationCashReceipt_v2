package ru.clevertec.ilkevich.receipt.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<List<CashReceipt>> getAllCashReceipt() {
        log.debug(CashReceiptController.class + ". Start method getAllCashReceipt");
        List<CashReceipt> cashReceiptList = abstractService.getAll();
        return ResponseEntity.ok(cashReceiptList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashReceipt> getCashReceiptById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method getCashReceiptById with id = " + id);
        CashReceipt cashReceipt = (CashReceipt) abstractService.findById(id);
        return ResponseEntity.ok(cashReceipt);
    }

    @PostMapping()
    public ResponseEntity<?> saveNewCashReceiptWithDefaultStore(@Valid @RequestBody CashReceiptSaveDto cashReceiptDto) {
        CashReceipt cashReceipt = cashReceiptDto.toCashReceipt();
        log.info("Start method saveNewCashReceiptWithDefaultStore " + cashReceipt);
        abstractService.save(cashReceipt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/shop/{id}")
    public ResponseEntity<?> saveNewCashReceiptWithStoreId(@PathVariable("id") @Min(1) Long id,
                                                           @Valid @RequestBody CashReceiptSaveDto cashReceiptDto) {
        CashReceipt cashReceipt = cashReceiptDto.toCashReceipt();
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(id);
        cashReceipt.setShopInfo(shopInfo);
        log.info(String.format("Start method saveNewCashReceiptWithStoreId id shop = %d, " +
                "cashReceipt = %s ", id, cashReceipt));
        abstractService.save(cashReceipt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashReceiptUpdateDto> updateCashReceipt(@PathVariable("id") @Min(1) Long id,
                                                                  @Valid @RequestBody CashReceiptUpdateDto receiptUpdateDto) {
        receiptUpdateDto.setCheckNumber(id);
        CashReceipt cashReceipt = receiptUpdateDto.toCashReceipt();
        log.info(String.format("Start method updateCashReceipt with id = %d " +
                "and cashReceipt = %s", id, cashReceipt));
        receiptUpdateDto = receiptUpdateDto.fromCashReceipt((CashReceipt) abstractService.update(cashReceipt));
        return ResponseEntity.ok(receiptUpdateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCashReceiptById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method deleteCashReceiptById with id = " + id);
        abstractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
