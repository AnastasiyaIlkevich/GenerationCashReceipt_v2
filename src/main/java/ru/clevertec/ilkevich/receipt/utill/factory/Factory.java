package ru.clevertec.ilkevich.receipt.utill.factory;

import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

public interface Factory <K, V> {

    AbstractCache<K, V> getCache(String cacheType, int capacity);
}
