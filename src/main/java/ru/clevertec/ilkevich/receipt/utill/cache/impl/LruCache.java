package ru.clevertec.ilkevich.receipt.utill.cache.impl;

import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The implementation of the cache interface that LRU(The Least Recently Used)
 * an algorithm for deleting a cache that has not been used for a long time.
 *
 * @param <K> the type of keys stored in the cache
 * @param <V> the type of values stored in the cache
 * @author Anastasiya Ilkevich
 * @version 1.0
 */

public class LruCache<K, V> implements AbstractCache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;

    /**
     * Creates a new LRU cache with the specified capacity in application.yml.
     *
     * @param capacity the maximum size of the capacity in which key-value
     *                 pairs will be placed to be stored in the cache
     * @throws IllegalArgumentException if the capacity is negative
     */
    public LruCache(int capacity) {

        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        cache = new LinkedHashMap<>(capacity);

    }

    /**
     * Returns the value to which the specified key is mapped.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    @Override
    public V get(K key) {

        if (!cache.containsKey(key)) {
            return null;
        }
        return cache.get(key);

    }

    /**
     * Binds the specified value to the specified key in this cache.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    @Override
    public void put(K key, V value) {

        if (cache.containsKey(key)) {
            cache.replace(key, value);
        } else if (cache.size() == capacity) {
            K firstKey = cache.keySet()
                    .iterator()
                    .next();
            cache.remove(firstKey);
        }
        cache.put(key, value);

    }

    /**
     * Removes the value that the specified key corresponds to.
     *
     * @param key the key whose associated value is to be removed
     */
    @Override
    public void remove(K key) {

        cache.remove(key);

    }

    /**
     * Returns a collection of cache values.
     *
     * @return a collection of cache values
     */
    @Override
    public Collection<V> values() {
        return cache.values();
    }

    /**
     * Checks if this key is in the cache.
     *
     * @return a Boolean value of whether the key is in the cache
     */
    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    /**
     * Returns the current size of cache.
     *
     * @return the current size of cache
     */
    @Override
    public int size() {
        return cache.size();
    }

}