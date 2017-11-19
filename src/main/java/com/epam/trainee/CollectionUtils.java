package com.epam.trainee;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CollectionUtils {

    public static <T> Map<T, Long> collectionElementsCount(Collection<T> collection) {
        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
