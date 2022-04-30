package com.bgr.genesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FunctionalProgrammingCommandLineApp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //functional programming practices.

        //map();
        //filter();
        //reduce();
        //collectors();
        //parallelStream();
        //composition();
    }

    private void map() {
        // map()
        Integer[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> listOfInteger = new ArrayList<>(Arrays.asList(intArray));

        Function<Integer, Integer> timesTwo = x -> x * 2;

        List<Integer> doubled = listOfInteger
            .stream()
            .map(timesTwo)
            .collect(Collectors.toList());

        System.out.println(doubled);
    }

    private void filter() {
        // filter()
        String[] array = {"celal", "şengör", "ilber", "ortaylı", "emrah", "sefa", "gürkan"};
        List<String> nameList = new ArrayList<>(Arrays.asList(array));

        Function<Integer, Predicate<String>> lengthTest = (minLength) -> {
            return (str) -> str.length() > minLength;
        };

        Predicate<String> isLongerThan6 = lengthTest.apply(6);

        List<String> longWords = nameList
            .stream()
            .filter(isLongerThan6)
            .collect(Collectors.toList());

        System.out.println(longWords);
    }

    private void reduce() {
        // reduce
        Integer[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> integers = new ArrayList<>(Arrays.asList(intArray));

        BinaryOperator<Integer> getSum = (acc, x) -> {
            Integer result = acc + x;
            System.out.println("acc: " + acc + ", x: " + x + ", result: " + result);
            return result;
        };

        Integer sum = integers
            .stream()
            .reduce(0, getSum);
        System.out.println(sum);
    }

    private void collectors(){
        // Collectors
        String[] stringArray = {"celal", "şengör", "ilber", "ortaylı", "emrah", "sefa", "gürkan"};
        List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));

        Map<Integer, List<String>> wordLengthMap = stringList
            .stream()
            .collect(Collectors.groupingBy((word) -> word.length()));

        Map<Boolean, List<String>> wordLengthMapPartition = stringList
            .stream()
            .collect(Collectors.partitioningBy((word) -> word.length() > 5));

        System.out.println(wordLengthMapPartition);
    }

    private void parallelStream() {
        // Parallel Streams
        String[] wordsArr = {"hello", "apple", "functional", "programming", "is", "cool"};
        List<String> words = new ArrayList<>(Arrays.asList(wordsArr));

        List<String> processedWords = words
            .parallelStream()
            .map((word) -> {
                System.out.println("Uppercasing " + word);
                return word.toUpperCase(Locale.ENGLISH);
            })
            .map((word) -> {
                System.out.println("Adding exclamation point to " + word);
                return word + "!";
            })
            .collect(Collectors.toList());

        System.out.println(processedWords);
    }

    private void composition() {
        //Composition
        Function<Integer, Integer> timesTwoFunc = x -> x * 2;
        Function<Integer, Integer> minusOne = x -> x - 1;

        Function<Integer, Integer> timesTwoMinusOneCompose = timesTwoFunc.compose(minusOne);
        Function<Integer, Integer> timesTwoMinusOne = timesTwoFunc.andThen(minusOne);

        System.out.println(timesTwoMinusOneCompose.apply(5));
        System.out.println(timesTwoMinusOne.apply(5));
    }
}
