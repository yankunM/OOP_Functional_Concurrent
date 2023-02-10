import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        System.out.println("Problem 2: ______________________________________________");
        List<Double> numbers = Arrays.asList(-0.5, 2d, 3d, 0d, 4d); // documentation example
        List<HigherOrderUtils.NamedBiFunction<Double, Double, Double>> operations = Arrays.asList(HigherOrderUtils.add, HigherOrderUtils.multiply, HigherOrderUtils.add, HigherOrderUtils.divide);
        Double d = HigherOrderUtils.zip(numbers, operations); // expected correct value: 1.125
        System.out.println(d);
// different use case, not with NamedBiFunction objects
        List<String> strings = Arrays.asList("a", "n", "t");
// note the syntax of this lambda expression
        BiFunction<String, String, String> concat = (s, t) -> s + t;
        String s = HigherOrderUtils.zip(strings, Arrays.asList(concat, concat)); // expected correct value: "ant"
        System.out.println(s);
        System.out.println("___________________________________________________________");

        System.out.println("Problem 3: ______________________________________________");
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
        // you have to figure out the data type in the line below
        Set<Function<Integer,Integer>> bijections = BijectionGroup.bijectionsOf(a_few);
        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });
        System.out.println();
        List<Function<Integer, Integer>> listOfBij = new ArrayList<>();
        for(Function<Integer, Integer> function: bijections){
//            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, function.apply(n)));
//            System.out.println();
            listOfBij.add(function);
        }
        Group<Function<Integer, Integer>> g = BijectionGroup.bijectionGroup(a_few);
        System.out.print("f1: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, listOfBij.get(1).apply(n)));
        Function<Integer, Integer> f1 = listOfBij.get(1);
        Function<Integer, Integer> f2 = g.inverseOf(f1);
        System.out.println();
        System.out.print("f2: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f2.apply(n)));
        Function<Integer, Integer> id = g.identity();
        System.out.println();
        System.out.print("id: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, id.apply(n)));
        System.out.println();
        // Check Binary Operation
        System.out.println("----------------------");
        System.out.println("Checking Binary Operations: ");
        Function<Integer, Integer> bo1 = g.binaryOperation(f1, g.identity());
        System.out.print("f1 and e: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, bo1.apply(n)));
        System.out.println();
        Function<Integer, Integer> bo2 = g.binaryOperation( g.identity(), f2);
        System.out.print("e and f2: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, bo2.apply(n)));
        System.out.println();
        System.out.print("f1 and f2: ");
        Function<Integer, Integer> bo3 = g.binaryOperation(f1, f2);
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, bo3.apply(n)));
        System.out.println();
        Function<Integer, Integer> f3 = listOfBij.get(5);
        System.out.println();
        System.out.print("f3: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f3.apply(n)));
        System.out.println();

        System.out.println("(f1 f2) f3 and f1 (f2 f3): ");
        Function<Integer, Integer> bo4 = g.binaryOperation(g.binaryOperation(f1,f2),f3);
        System.out.print("(f1 f2) f3: ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, bo4.apply(n)));
        Function<Integer, Integer> bo5 = g.binaryOperation(f1,g.binaryOperation(f2,f3));
        System.out.println();
        System.out.print("f1 (f2 f3): ");
        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, bo5.apply(n)));
//        Function<Integer, Integer> f1 = bijectionsOf(a_few).stream().;
//        Function<Integer, Integer> f2 = g.inverseOf(f1);
//        Function<Integer, Integer> id = g.identity();
//        a_few.forEach(n -> System.out.printf("%d --> %d; ", n, f1.apply(n)));

    }
}