package ru.clevertec.ilkevich.receipt.utill.factory;

import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;
import ru.clevertec.ilkevich.receipt.utill.cache.impl.LfuCache;
import ru.clevertec.ilkevich.receipt.utill.cache.impl.LruCache;

public class CacheFactory <K, V> implements Factory<K, V> {

    @Override
    public AbstractCache<K, V> getCache(String cacheName, int capacity) {
        return switch (cacheName) {
            case "LRU"-> new LruCache<>(capacity);
            case "LFU"-> new LfuCache<>(capacity);
            default -> throw new IllegalArgumentException("No cache algorithm specified");
        };
    }
}
