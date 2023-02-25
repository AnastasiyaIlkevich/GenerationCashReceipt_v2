package ru.clevertec.ilkevich.receipt.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.ilkevich.receipt.exceptions.IdNotFoundException;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository;
import ru.clevertec.ilkevich.receipt.service.AbstractService;

import java.util.List;

@Component("ShopInfo")
public class ShopInfoServiceImpl implements AbstractService<ShopInfo, Long> {

    private final ShopInfoRepository shopInfoRepository;

    @Autowired
    public ShopInfoServiceImpl(ShopInfoRepository shopInfoRepository) {
        this.shopInfoRepository = shopInfoRepository;
    }

    @Override
    public List<ShopInfo> getAll() {
        return shopInfoRepository.findAll();
    }

    @Override
    public ShopInfo findById(Long id) {
        return shopInfoRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    @Override
    public void save(ShopInfo shopInfo) {
        shopInfoRepository.save(shopInfo);
    }

    @Override
    public ShopInfo update(ShopInfo shopInfo) {
        shopInfoRepository.findById(shopInfo.getId()).orElseThrow(IdNotFoundException::new);
        return shopInfoRepository.save(shopInfo);
    }

    @Override
    public void deleteById(Long id) {
        shopInfoRepository.deleteById(id);
    }
}
