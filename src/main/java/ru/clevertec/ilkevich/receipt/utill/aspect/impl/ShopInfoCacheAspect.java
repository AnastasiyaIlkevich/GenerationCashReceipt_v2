package ru.clevertec.ilkevich.receipt.utill.aspect.impl;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ilkevich.receipt.model.ShopInfo;
import ru.clevertec.ilkevich.receipt.utill.aspect.AbstractCacheAspect;
import ru.clevertec.ilkevich.receipt.utill.factory.CacheFactory;
import ru.clevertec.ilkevich.receipt.utill.factory.Factory;
import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

import java.util.Optional;

@Aspect
@Configuration
public class ShopInfoCacheAspect implements AbstractCacheAspect<ShopInfo, ProceedingJoinPoint> {

    private final AbstractCache<Long, ShopInfo> cache;

    public ShopInfoCacheAspect(
            @Value("${cache.cache-names}") String cacheType,
            @Value("${cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, ShopInfo> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository.findById(..))")
    public Optional<ShopInfo> aroundFindById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        } else {
            Optional<ShopInfo> shopInfoOptional = (Optional<ShopInfo>) joinPoint.proceed();
            shopInfoOptional.ifPresent(shopInfo -> cache.put(shopInfo.getId(), shopInfo));
            return shopInfoOptional;
        }

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository.save(..))")
    public ShopInfo aroundCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        ShopInfo shopInfo = (ShopInfo) joinPoint.proceed();
        cache.put(shopInfo.getId(), shopInfo);
        return shopInfo;

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.ShopInfoRepository.deleteById(..))")
    public ShopInfo aroundDeleteById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        return (ShopInfo) joinPoint.proceed();

    }

}
