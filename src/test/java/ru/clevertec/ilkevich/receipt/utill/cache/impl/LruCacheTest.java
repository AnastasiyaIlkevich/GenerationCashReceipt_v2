package ru.clevertec.ilkevich.receipt.utill.cache.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LruCacheTest {

    private final LruCache lruCache = new LruCache(3);
    private static Map<Integer, String> maps;

    @BeforeAll
    static void setUp() {
        maps = new HashMap<>();
        maps.put(2, "the second object");
        maps.put(3, "the third object");
        maps.put(4, "the fourth object");
    }


    @Test
    @DisplayName("Get from the cache is equal to the value")
    void checkGetShouldReturnCacheValue() {

        lruCache.put(1, "the first object");

        assertThat(lruCache.get(1)).isEqualTo("the first object");
    }


    @Test
    @DisplayName("Recording values and comparing contents")
    void checkPutShouldReturnCacheValues() {
        lruCache.put(1, "the first object");
        lruCache.put(2, "the second object");
        lruCache.put(3, "the third object");
        lruCache.put(4, "the fourth object");

        assertThat(lruCache.values().toString()).isEqualTo(maps.values().toString());
    }

    @Test
    @DisplayName("Number of items after remove")
    void checkRemoveShouldReturnCacheSize1() {
        lruCache.put(1, "the first object");
        lruCache.put(2, "the second object");
        lruCache.remove(1);

        System.out.println(lruCache);
        assertThat(lruCache.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Getting all values ")
    void checkValuesShouldReturnToEqual() {
        lruCache.put(2, "the second object");
        lruCache.put(3, "the third object");
        lruCache.put(4, "the fourth object");
        assertThat(lruCache.values().toString()).isEqualTo(maps.values().toString());
    }

    @Test
    @DisplayName("Checking the existence of the key")
    void checkContainsKeyShouldReturnTrue() {
        lruCache.put(1, "the first object");
        assertThat(lruCache.containsKey(1)).isTrue();
    }

    @Test
    @DisplayName("Number of items in the cache")
    void checkSizeShouldReturnSize2() {
        lruCache.put(2, "the second object");
        lruCache.put(3, "the third object");
        assertThat(lruCache.size()).isEqualTo(2);
    }
}