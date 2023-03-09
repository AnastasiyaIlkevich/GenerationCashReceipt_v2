package ru.clevertec.ilkevich.receipt.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ilkevich.receipt.dto.ShopUpdateDto;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

/**
 * Controller for entity ShopInfo (CRUD)
 *
 * @author Ilkevich Anastasiya
 * @version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/shop")
public class ShopInfoController {


    private final AbstractService abstractService;

    @Autowired
    public ShopInfoController(@Qualifier("ShopInfo") AbstractService abstractService) {
        this.abstractService = abstractService;
    }

    @GetMapping
    private ResponseEntity<List<ShopInfo>> getAllShopInfo() {
        log.debug(ShopInfoController.class + ". Start method getAllShopInfo");
        List<ShopInfo> shopInfoList = abstractService.getAll();
        return ResponseEntity.ok(shopInfoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopInfo> getShopInfoById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method getShopInfoById with id = " + id);
        ShopInfo shopInfo = (ShopInfo) abstractService.findById(id);
        return ResponseEntity.ok(shopInfo);
    }

    @PostMapping()
    public ResponseEntity<?> saveShopInfo(@Valid @RequestBody ShopUpdateDto shopDto) {
        ShopInfo shopInfo = shopDto.toShopInfo();
        log.info("Start method saveShopInfo " + shopInfo);
        abstractService.save(shopInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopUpdateDto> updateShopInfo(@PathVariable("id") @Min(1) Long id,
                                                        @Valid @RequestBody ShopUpdateDto shopDto) {
        shopDto.setId(id);
        ShopInfo shopInfo = shopDto.toShopInfo();
        log.info(String.format("Start method updateShopInfo with id = %d " +
                "and shopInfo = %s", id, shopInfo));
        shopDto = shopDto.fromShopInfo((ShopInfo) abstractService.update(shopInfo));
        return ResponseEntity.ok(shopDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShopInfoById(@PathVariable("id") @Min(1) Long id) {
        log.info("Start method deleteShopInfoById with id = " + id);
        abstractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
