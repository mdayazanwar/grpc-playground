package com.ayaz.sec03;

import com.ayaz.models.sec03.Address;
import com.ayaz.models.sec03.School;
import com.ayaz.models.sec03.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04Composition {
    private static final Logger log  = LoggerFactory.getLogger(Lec04Composition.class);

    public static void main(String[] args) {


        // create student

        Address address = Address.newBuilder()
                .setState("123 main street")
                .setCity("Bengaluru")
                .setState("Karnataka")
                .build();
        var student = Student.newBuilder()
                .setName("Ayaz")
                .setAddress(address)
                .build();

        // create school

        var school = School.newBuilder()
                .setName("high school")
                .setAddress(Address.newBuilder()
                        .setState("234 main street")
                        .setCity("Bengaluru")
                        .setState("Karnataka")
                        .build())
                .build();

        log.info("Student: {}", student);
        log.info("School: {}", school);
    }

}
