package ru.clevertec.ilkevich.receipt.utill.cache.impl;


import ru.clevertec.ilkevich.receipt.utill.cache.AbstractCache;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;


/**
 * The implementation of the LFU (Least Frequently Used)
 * cache interface by an algorithm in which, when the maximum
 * cache capacity is reached, the element with the
 * least access to it is deleted.
 *
 * @param <K> the type of keys stored in the cache
 * @param <V> the type of values stored in the cache
 * @author Anastasiya Ilkevich
 * @version 1.0
 */
public class LfuCache<K, V> implements AbstractCache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, Integer> frequencies;
    private final Map<Integer, LinkedHashSet<K>> orderedKeys;

    private int minKeyFrequency;

    /**
     * Constructs a new LFU cache with the specified capacity in application.yml.
     *
     * @param capacity the maximum size of the capacity in which key-value
     *                 pairs will be placed to be stored in the cache
     * @throws IllegalArgumentException if the capacity is negative
     */
    public LfuCache(int capacity) {

        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.frequencies = new HashMap<>();
        this.orderedKeys = new HashMap<>();
        this.minKeyFrequency = 0;

    }

    /**
     * Binds the passed passed value to the key in the cache and, if there is one,
     * updates the value in the cache and increases its minKeyFrequency.
     * If it is not present, it creates a new key and writes the value,
     * which leads to an increase in the cache.
     * If the cache is full, then the most unclaimed element is deleted.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    @Override
    public void put(K key, V value) {

        if (cache.containsKey(key)) {
            int frequency = frequencies.get(key);
            frequencies.put(key, frequency + 1);

            orderedKeys.get(frequency).remove(key);
            orderedKeys.putIfAbsent(frequency + 1, new LinkedHashSet<>());
            orderedKeys.get(frequency + 1).add(key);
            if (frequency == minKeyFrequency && orderedKeys.get(frequency).isEmpty())
                minKeyFrequency++;
            cache.replace(key, value);
        } else if (cache.size() == capacity) {
            var setFrequency = orderedKeys.get(minKeyFrequency);
            K leastUsedKey = setFrequency.iterator().next();
            setFrequency.remove(leastUsedKey);
            frequencies.remove(leastUsedKey);
            cache.remove(leastUsedKey);
        } else {
            frequencies.put(key, 1);
            orderedKeys.putIfAbsent(1, new LinkedHashSet<>());
            orderedKeys.get(1).add(key);
            minKeyFrequency = 1;
            cache.put(key, value);
        }

    }

    /**
     * Returns the value to which the specified key is mapped.
     * If the key and value exist in the cache, their frequency increases.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    @Override
    public V get(K key) {

        if (!cache.containsKey(key)) {
            return null;
        }
        V value = cache.get(key);
        int frequency = frequencies.get(key);
        frequencies.put(key, frequency + 1);

        orderedKeys.get(frequency).remove(key);
        orderedKeys.putIfAbsent(frequency + 1, new LinkedHashSet<>());
        orderedKeys.get(frequency + 1).add(key);
        if (frequency == minKeyFrequency && orderedKeys.get(frequency).isEmpty())
            minKeyFrequency++;
        return value;

    }

    /**
     * Removes the value that the specified key corresponds to.
     *
     * @param key the key whose associated value is to be removed
     */
    @Override
    public void remove(K key) {
        int frequency = frequencies.remove(key);
        orderedKeys.get(frequency).remove(key);
        cache.remove(key);
        frequencies.remove(key);
        if (frequency == minKeyFrequency && orderedKeys.get(frequency).isEmpty())
            minKeyFrequency++;
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