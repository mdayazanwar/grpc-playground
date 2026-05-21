package com.ayaz.sec03;

import com.ayaz.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Lec02Serialization {

    private static final Logger log  = LoggerFactory.getLogger(Lec02Serialization.class);

    private static final Path PATH = Path.of("person.out");

    public static void main(String[] args) throws IOException {
        var person = Person.newBuilder()
                .setLastName("Anwar")
                .setAge(35)
                .setEmail("TestEmailAyaz@gmail.com")
                .setEmployed(true)
                .setSalary(1000.234)
                .setBankAccountNumber(123456789L)
                .setBalace(-10000)
                .build();

        serialize(person);
        log.info("{}", deserialize());
        log.info("equals: {}", person.equals(deserialize()));
        log.info("Byte Length: {}", person.toByteArray().length);
    }

    // writeTo Method is used to serialize
    public static void serialize(Person person) throws IOException {
        try(var stream = Files.newOutputStream(PATH)) {
            person.writeTo(stream);
        }
    }
    // parseFrom is used to deserialize
    public static Person deserialize() throws IOException {
        try(var stream = Files.newInputStream(PATH)) {
            return Person.parseFrom(stream);
        }
    }
}
