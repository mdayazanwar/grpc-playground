package com.ayaz.sec04;

import com.ayaz.models.common.Address;
import com.ayaz.models.common.BodyStyle;
import com.ayaz.models.common.Car;
import com.ayaz.models.sec04.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Import {

    private static final Logger log = LoggerFactory.getLogger(Lec01Import.class);

    public static void main(String[] args) {
        var address = Address.newBuilder()
                .setCity("Bengaluru")
                .build();

        var car = Car.newBuilder()
                .setBodyStyle(BodyStyle.COUPE)
                .build();

        var person = Person.newBuilder()
                .setLastName("Anwar")
                .setAge(35)
                .setAddress(address)
                .setCar(car)
                .build();

        log.info("Person: {}", person);
    }
}
