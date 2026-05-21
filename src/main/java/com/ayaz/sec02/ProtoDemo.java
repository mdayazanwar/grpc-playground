package com.ayaz.sec02;

import com.ayaz.models.sec02.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtoDemo {

    private static final Logger log = LoggerFactory.getLogger(ProtoDemo.class);

    public static void main(String[] args) {
        var person1 = createPerson();
        var person2 = createPerson();

        // Compare
        log.info("equals {}", person1.equals(person2));
        log.info("== {}", person1 == person2);

        //Mutable ?  No

        // Create Another instance with different value
        var person3  = person1.toBuilder().setName("mike").build();
        log.info("equals {}", person1.equals(person3));
        log.info("== {}", person1 == person3);

        //null ? can't pass null as a value when setting a field value. We need to use clear methods from lib to clear some field value from the object like beloow
        var person4 = person1.toBuilder().clearName().build();
        log.info("{}", person4);

    }

    private static Person createPerson() {
        return Person.newBuilder()
                .setName("Ayaz")
                .setAge(35)
                .build();
    }
}
