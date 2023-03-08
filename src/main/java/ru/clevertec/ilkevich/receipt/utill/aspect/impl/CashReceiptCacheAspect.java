package ru.clevertec.ilkevich.receipt.utill.aspect.impl;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ilkevich.receipt.model.CashReceipt;
import ru.clevertec.ilkevich.receipt.utill.aspect.AbstractCacheAspect;
import ru.clevertec.ilkevich.receipt.utill.factory.CacheFactory;
import ru.clevertec.ilkevich.receipt.utill.factory.Factory;
import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

import java.util.Optional;

@Aspect
@Configuration
public class CashReceiptCacheAspect implements AbstractCacheAspect<CashReceipt, ProceedingJoinPoint> {

    private final AbstractCache<Long, CashReceipt> cache;

    public CashReceiptCacheAspect(
            @Value("${cache.cache-names}") String cacheType,
            @Value("${cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, CashReceipt> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.CashReceiptRepository.findById(..))")
    public Optional<CashReceipt> aroundFindById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        } else {
            Optional<CashReceipt> cashReceiptOptionalOptional = (Optional<CashReceipt>) joinPoint.proceed();
            cashReceiptOptionalOptional.ifPresent(cashReceipt -> cache.put(cashReceipt.getCheckNumber(), cashReceipt));
            return cashReceiptOptionalOptional;
        }

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.CashReceiptRepository.save(..))")
    public CashReceipt aroundCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        CashReceipt cashReceipt = (CashReceipt) joinPoint.proceed();
        cache.put(cashReceipt.getCheckNumber(), cashReceipt);
        return cashReceipt;

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.CashReceiptRepository.deleteById(..))")
    public CashReceipt aroundDeleteById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        return (CashReceipt) joinPoint.proceed();

    }

}
