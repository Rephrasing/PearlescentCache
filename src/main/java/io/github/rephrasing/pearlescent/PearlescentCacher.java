package io.github.rephrasing.pearlescent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PearlescentCacher {

    private static final List<PearlescentCacheAdapter<?, ?>> registeredCacheAdapters = new ArrayList<>();

    public static <T, S> void registerCacheAdapter(PearlescentCacheAdapter<T, S> serializer)
    {
        if (getCacheAdapter(serializer.getObjectType(), serializer.getSerializeType()).isPresent()) return;
        registeredCacheAdapters.add(serializer);
    }

    public static <T, S> Optional<PearlescentCacheAdapter<T, S>> getCacheAdapter(Class<T> rawObjectClass, Class<S> serializeObjectClass) {
        Optional<PearlescentCacheAdapter<T, S>> optional = Optional.empty();

        for (PearlescentCacheAdapter<?, ?> serializer : registeredCacheAdapters) {
            if (serializer.getObjectType() == rawObjectClass && serializer.getSerializeType() == serializeObjectClass) {
                optional = Optional.of((PearlescentCacheAdapter<T, S>) serializer);
            }
        }
        return optional;
    }

}
