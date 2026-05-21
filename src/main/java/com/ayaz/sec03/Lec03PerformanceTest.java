package com.ayaz.sec03;

import com.ayaz.models.sec03.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Lec03PerformanceTest {

    private static final Logger log  = LoggerFactory.getLogger(Lec03PerformanceTest.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        var protoPerson = Person.newBuilder()
                .setLastName("Anwar")
                .setAge(35)
                .setEmail("TestEmailAyaz@gmail.com")
                .setEmployed(true)
                .setSalary(1000.234)
                .setBankAccountNumber(123456789L)
                .setBalace(-10000)
                .build();

        var jsonPerson = new JsonPerson("Anwar", 35,"TestEmailAyaz@gmail.com", true, 1000.234, 123456789L, -10000  );

        json(jsonPerson);
        proto(protoPerson);

//        for( int i =0; i < 5; i++) {
//            runTest("Json", ()-> json(jsonPerson));
//            runTest("proto", ()-> proto(protoPerson));
//        }
    }

    private static void proto(Person person) {
        var bytes = person.toByteArray();
        log.info("proto bytes length: {}", bytes.length);
        try {
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson person) {
        try {
            var bytes =  mapper.writeValueAsBytes(person);
            log.info("json bytes length: {}", bytes.length);

            mapper.readValue(bytes, JsonPerson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static  void runTest(String testName, Runnable runnable) {
        var start = System.currentTimeMillis();
        for(int  i = 0; i< 5_000_000; i++) {
            runnable.run();
        }
        var end = System.currentTimeMillis();
        log.info("time taken for {} - {}", testName, end-start);
    }

}
