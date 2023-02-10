import java.util.Collection;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleUtils {
    /**
     * Find and return the least element from a collection of given elements that are comparable.
     *
     * @param items: the given collection of elements
     * @param from_start: a boolean flag that decides how ties are broken.
     * If true, the element encountered earlier in the
     * iteration is returned, otherwise the later element is returned.
     * @param <T>: the type parameter of the collection (i.e., the items are all of type T).
     * @return the least element in items, where ties are
     * broken based on from_start.
     */
    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start){
        return from_start
                ? items.stream().reduce((a,b)-> a.compareTo(b) < 0 ? a : b).orElse(null)
                : items.stream().reduce((a,b)-> b.compareTo(a) < 0 ? b : a).orElse(null);
    }

    /**
     * Flattens a map to a sequence of Strings, where each element in the list is formatted
     * as "key -> value" (i.e., each key-value pair is converted to a string in this specific format).
     *
     * @param aMap the specified input map.
     * @param <K> the type parameter of keys in aMap.
     * @param <V> the type parameter of values in aMap.
     * @return the flattened list representation of aMap.
     */
    public static <K, V> List<String> flatten(Map<K, V> aMap){
        return aMap.entrySet().stream()
                .map(elem -> elem.getKey() + " -> " + elem.getValue())
                .collect(Collectors.toList());
    }
}
