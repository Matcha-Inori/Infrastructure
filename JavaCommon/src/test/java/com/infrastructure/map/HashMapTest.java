package com.infrastructure.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest
{
    @Test
    public void testLoadFactor() throws Exception
    {
        //loadFactor是可以大于1的
        float loadFactor = 1.5f;
        Map<String, String> hashMap = new HashMap<>(4, loadFactor);
        hashMap.put("A", "AA");
        hashMap.put("B", "AA");
        hashMap.put("C", "AA");
        hashMap.put("D", "AA");
        hashMap.put("E", "AA");
        hashMap.put("F", "AA");
        hashMap.put("G", "AA");
    }
}
