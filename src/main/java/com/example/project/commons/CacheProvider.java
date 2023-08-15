package com.example.project.commons;

import com.example.project.Domain.Item;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheProvider {

    public static Cache<Integer, Item> createCache() {
        CacheConfiguration<Integer, Item> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class, Item.class,
                        ResourcePoolsBuilder.heap(100)) // Set the maximum number of entries
                .build();

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        return cacheManager.createCache("itemCache", cacheConfig);
    }
}
