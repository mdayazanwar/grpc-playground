package com.ayaz.sec03;

import com.ayaz.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Scalar {

    private static final Logger log = LoggerFactory.getLogger(Lec01Scalar.class);

    public static void main(String[] args) {


        // If you don't set any of the below field, that field will be marked as optional.
        // In the below example when we log person, it is only going to print me Name and Age and remaining all other field will be skipped
        var person = Person.newBuilder()
                .setLastName("Anwar")
                .setAge(35)
//                .setEmail("TestEmailAyaz@gmail.com")
//                .setEmployed(true)
//                .setSalary(1000.234)
//                .setBankAccountNumber(123456789L)
//                .setBalace(-10000)
                .build();

        log.info("{}", person);
    }
}
