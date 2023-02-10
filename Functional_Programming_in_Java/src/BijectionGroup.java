import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BijectionGroup {
    public static <T> Set<Function<T,T>> bijectionsOf(Set<T> domain){
        Set<Function<T,T>> result = new HashSet<>();
        // Get 2D Array permutations
        int n = domain.size();
        List<T> list1 = new ArrayList<>(domain);
        List<List<T>> list = permute(list1);
        for(List<T> l: list){
            Map<T, T> map = new HashMap<>();
            for(int i = 0; i < n; i++){
                map.put(list1.get(i), l.get(i));
            }
            Function<T,T> function = new Function<T,T>() {
                final Map<T,T> thismap = map;

                @Override
                public T apply(T t) {
                    return thismap.get(t);
                }
            };
            result.add(function);
        }
        return result;
    }

    private static <T> List<List<T>> permute(List<T> nums) {
        List<List<T>> result = new ArrayList<>();
        Permutation(0, nums, result);
        return result;
    }

    private static <T> void Permutation(int i, List<T> nums, List<List<T>> result) {
        if (i == nums.size() - 1) {
            List<T> list = new ArrayList<>(nums);
            result.add(list);
        } else {
            for (int j = i, l = nums.size(); j < l; j++) {
                T temp = nums.get(j);
                nums.set(j, nums.get(i));
                nums.set(i, temp);
                Permutation(i + 1, nums, result);
                temp = nums.get(j);
                nums.set(j, nums.get(i));
                nums.set(i, temp);
            }
        }
    }

    public static <T> Group<Function<T, T>> bijectionGroup(Set<T> domain){
        Group<Function<T, T>> group = new Group<Function<T, T>>() {
            @Override
            public Function<T, T> binaryOperation(Function<T, T> one, Function<T, T> other) {
                return one.andThen(other);
            }

            @Override
            public Function<T, T> identity() {
                List<T> arr = new ArrayList<>(domain);
                Map<T, T> hashmap = new HashMap<>();
                for (T t : arr) {
                    hashmap.put(t, t);
                }
                Function<T, T> function = new Function<T, T>() {
                    final Map<T,T> thismap = hashmap;
                    @Override
                    public T apply(T t) {
                        return thismap.get(t);
                    }
                };
                return function;
            }

            @Override
            public Function<T, T> inverseOf(Function<T, T> ttFunction) {
                List<T> arr = new ArrayList<>(domain);
                Map<T, T> hashmap = new HashMap<>();
                for(int i = 0; i < arr.size(); i++) {
                    hashmap.put(arr.get(i), ttFunction.apply(arr.get(i)));
                }
                //reverse hashmap
                Map<T,T> reversedmap = new HashMap<>();
                for(T key: hashmap.keySet()){
                    reversedmap.put(hashmap.get(key), key);
                }
                Function<T, T> function = new Function<T, T>() {
                    final Map<T,T> thismap = reversedmap;
                    @Override
                    public T apply(T t) {
                        return thismap.get(t);
                    }
                };
                return function;
            }
        };

        return group;
    }

//    public static void main(String... args) {
//        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
////        // you have to figure out the data type in the line below
////        Set<Function<Integer,Integer>> bijections = bijectionsOf(a_few);
////        bijections.forEach(aBijection -> {
////            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
////            System.out.println();
////        });
//        Group<Function<Integer, Integer>> g = bijectionGroup(a_few);
//
//        Set<Function<Integer, Integer>> bijections = bijectionsOf(a_few);
//        List<Function<Integer, Integer>> listOfBij = new ArrayList<>();
//        for(Function<Integer, Integer> function: bijections){
////            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, function.apply(n)));
////            System.out.println();
//            listOfBij.add(function);
//        }
//        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, listOfBij.get(1).apply(n)));
//        Function<Integer, Integer> f1 = listOfBij.get(1);
//        Function<Integer, Integer> f2 = g.inverseOf(f1);
//        System.out.println();
//        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f2.apply(n)));
//        Function<Integer, Integer> id = g.identity();
//        System.out.println();
//        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, id.apply(n)));
//        // Check Binary Operation
//        Function<Integer, Integer> bo = g.binaryOperation(f1, g.identity());
//
////        Function<Integer, Integer> f1 = bijectionsOf(a_few).stream().;
////        Function<Integer, Integer> f2 = g.inverseOf(f1);
////        Function<Integer, Integer> id = g.identity();
////        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f1.apply(n)));
//
//    }
}
