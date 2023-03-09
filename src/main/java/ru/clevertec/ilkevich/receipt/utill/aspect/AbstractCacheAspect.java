package ru.clevertec.ilkevich.receipt.utill.aspect;

import java.util.Optional;

public interface AbstractCacheAspect<V, P> {

    Optional<V> aroundFindById(P joinPoint) throws Throwable;


    V aroundCreate(P joinPoint) throws Throwable;


    public V aroundDeleteById(P joinPoint) throws Throwable;

}
