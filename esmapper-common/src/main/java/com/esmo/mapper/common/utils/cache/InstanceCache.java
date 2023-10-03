package com.esmo.mapper.common.utils.cache;

public interface InstanceCache {
    default <T> InstanceCacheValue<T> getCacheValues(String key, Object in) {
        return getCacheValues(key == null ? 0 : key.hashCode(), in);
    }
    <T> InstanceCacheValue<T> getCacheValues(int hashKey, Object in);
}
