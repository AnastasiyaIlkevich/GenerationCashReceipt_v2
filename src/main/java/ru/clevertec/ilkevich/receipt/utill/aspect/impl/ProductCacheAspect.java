package ru.clevertec.ilkevich.receipt.utill.aspect.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.ilkevich.receipt.model.Product;
import ru.clevertec.ilkevich.receipt.utill.aspect.AbstractCacheAspect;
import ru.clevertec.ilkevich.receipt.utill.factory.CacheFactory;
import ru.clevertec.ilkevich.receipt.utill.factory.Factory;
import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

import java.util.Optional;

@Aspect
@Configuration
public class ProductCacheAspect implements AbstractCacheAspect<Product, ProceedingJoinPoint>{

    private final AbstractCache<Long, Product> cache;

    public ProductCacheAspect(
            @Value("${cache.cache-names}") String cacheType,
            @Value("${cache.capacity}") int cacheCapacity
    ) {
        Factory<Long, Product> factory = new CacheFactory<>();
        cache = factory.getCache(cacheType, cacheCapacity);
    }


    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.ProductRepository.findById(..))")
    public Optional<Product> aroundFindById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        }
        Optional<Product> productOptional = (Optional<Product>) joinPoint.proceed();
        productOptional.ifPresent(product -> cache.put(product.getId(), product));
        return productOptional;

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.ProductRepository.save(..))")
    public Product aroundCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        Product product = (Product) joinPoint.proceed();
        cache.put(product.getId(), product);
        return product;

    }

    @Around("execution(* ru.clevertec.ilkevich.receipt.repository.ProductRepository.deleteById(..))")
    public Product aroundDeleteById(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        return (Product) joinPoint.proceed();

    }
}
