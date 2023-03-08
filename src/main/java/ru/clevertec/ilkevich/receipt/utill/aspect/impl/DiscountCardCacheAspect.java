package ru.clevertec.ilkevich.receipt.utill.aspect.impl;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ilkevich.receipt.model.DiscountCard;
import ru.clevertec.ilkevich.receipt.utill.aspect.AbstractCacheAspect;
import ru.clevertec.ilkevich.receipt.utill.factory.CacheFactory;
import ru.clevertec.ilkevich.receipt.utill.factory.Factory;
import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

import java.util.Optional;

@Aspect
@Configuration
public class DiscountCardCacheAspect implements AbstractCacheAspect<DiscountCard, ProceedingJoinPoint> {

    private final AbstractCache<Long, DiscountCard> cache;

    public DiscountCardCacheAspect(
            @Value("${cache.cache-names}") String cacheType,
            @Value("${cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, DiscountCard> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository.findById(..))")
    public Optional<DiscountCard> aroundFindById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        } else {
            Optional<DiscountCard> discountCardOptional = (Optional<DiscountCard>) joinPoint.proceed();
            discountCardOptional.ifPresent(discountCard -> cache.put(discountCard.getId(), discountCard));
            return discountCardOptional;
        }

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository.save(..))")
    public DiscountCard aroundCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        DiscountCard discountCard = (DiscountCard) joinPoint.proceed();
        cache.put(discountCard.getId(), discountCard);
        return discountCard;

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository.deleteById(..))")
    public DiscountCard aroundDeleteById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        return (DiscountCard) joinPoint.proceed();

    }


//    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.DiscountCardRepository.findAll(..))))")
//    public List<DiscountCard> aroundFindByNumberMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//    return discountCardList;
//    }

}
