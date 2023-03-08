package ru.clevertec.ilkevich.receipt.utill.cache;

import java.util.Collection;

public interface AbstractCache<K, V> {

    V get(K key);

    void put(K key, V value);

    void remove(K key);

    Collection<V> values();

    int size();

    boolean containsKey(K key);
}
