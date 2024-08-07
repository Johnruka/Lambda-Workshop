package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;


    public static void exercise1(String message) {
        System.out.println(message);
        storage.findMany(p -> p.getFirstName().equals("Erik")).forEach(System.out::println);
        System.out.println("----------------------");
    }


    public static void exercise2(String message) {
        System.out.println(message);
        storage.findMany(person -> person.getGender().equals(Gender.FEMALE)).forEach(System.out::println);
        System.out.println("----------------------");
    }


    public static void exercise3(String message) {
        System.out.println(message);
        storage.findMany(person -> person.getBirthDate().isAfter(LocalDate.parse("1999-12-31"))).forEach(System.out::println);

        System.out.println("----------------------");
    }


    public static void exercise4(String message) {
        System.out.println(message);
        System.out.println(storage.findOne(p -> p.getId() == 123));

        System.out.println("----------------------");

    }


    public static void exercise5(String message) {

        System.out.println("----------------------");
        System.out.println(
                storage.findOneAndMapToString(
                        person -> person.getId() == 456,
                        person -> "Name: " + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate()
                )
        );
    }


    public static void exercise6(String message) {
        System.out.println(message);
        storage.findManyAndMapEachToString(
                person -> person.getGender() == Gender.MALE && person.getFirstName().startsWith("E"),
                Person::toString
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }


    public static void exercise7(String message) {
        System.out.println(message);
        storage.findManyAndMapEachToString(
                person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10,
                person -> person.getFirstName() + " " + person.getLastName() + " " + Period.between(person.getBirthDate(), LocalDate.now()).getYears() + " years"
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }


    public static void exercise8(String message) {
        System.out.println(message);
        storage.findAndDo(
                person -> person.getFirstName().equals("Ulf"),
                System.out::println
        );

        System.out.println("----------------------");
    }


    public static void exercise9(String message) {
        System.out.println(message);
        storage.findAndDo(
                person -> person.getLastName().toLowerCase().contains(person.getFirstName().toLowerCase()),
                System.out::println
        );


        System.out.println("----------------------");
    }


    public static void exercise10(String message) {
        System.out.println(message);
        storage.findAndDo(
                person -> person.getFirstName().equalsIgnoreCase(new StringBuilder(person.getFirstName()).reverse().toString()),
                person -> System.out.println(person.getFirstName() + " " + person.getLastName())
        );

        System.out.println("----------------------");
    }


    public static void exercise11(String message) {
        System.out.println(message);
        storage.findAndSort(
                person -> person.getFirstName().startsWith("A"),
                Comparator.comparing(Person::getBirthDate)
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }


    public static void exercise12(String message) {
        System.out.println(message);
        storage.findAndSort(
                person -> person.getBirthDate().getYear() < 1950,
                Comparator.comparing(Person::getBirthDate).reversed()
        ).forEach(System.out::println);

        System.out.println("----------------------");
    }


    public static void exercise13(String message) {
        System.out.println(message);
        Comparator<Person> compareLastName = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        };

        //With Lambda
        Comparator<Person> compareFirstName = (Person o1, Person o2) -> o1.getFirstName().compareTo(o2.getFirstName());
        //With Method Reference
        Comparator<Person> compareBirthDate = Comparator.comparing(Person::getBirthDate);
        //Stack
        Comparator<Person> all = compareLastName.thenComparing(compareFirstName).thenComparing(compareBirthDate);
        storage.findAndSort(all).forEach(System.out::println);

        /*
        storage.findAndSort(
                Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate)
        ).forEach(System.out::println);
        */

        System.out.println("----------------------");
    }
}
