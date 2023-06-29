package io.github.rephrasing.pearlescent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Represents a GenericSerializer
 * @param <T> The Object which is being serialized
 * @param <S> The Object which is being deserialized
 */
public abstract class PearlescentCacheAdapter<T, S> {

    private final List<T> cache = new ArrayList<>();

    abstract public S serialize(T t);
    abstract public T deserialize(S s);
    abstract public Class<T> getObjectType();
    abstract public Class<S> getSerializeType();


    /**
     * Finds an element in the cache using a Predicate filter
     * @param filter the Predicate filter
     * @return an Optional that contains the element if it met the predicate filter
     */
    public Optional<T> get(Predicate<T> filter) {
        return cache.stream().filter(filter).findFirst();
    }

    /**
     * Caches an element regardless if it already exists in cache
     * @param t the element
     */
    public void cache(T t) {
        cache.add(t);
    }

    /**
     * Searches through cache using a Predicate, If met it replaces the found element with the provided one. otherwise it adds the object to the cache
     * @param filter the Predicate filter
     * @param t the element
     */
    public void cacheOrReplace(Predicate<T> filter, T t) {
        Optional<T> met = cache.stream().filter(filter).findFirst();
        met.ifPresent(cache::remove);
        cache.add(t);
    }

    /**
     * Drops one or more elements if they meet the predicate filter
     * @param filter the Predicate filter
     */
    public void drop(Predicate<T> filter) {
        List<T> met = cache.stream().filter(filter).toList();
        if (met.isEmpty()) return;
        met.forEach(cache::remove);
    }
}
