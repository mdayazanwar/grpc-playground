package com.ayaz.sec03;

import com.ayaz.models.sec03.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07DefaultValue {

    private static final Logger log  = LoggerFactory.getLogger(Lec07DefaultValue.class);

    public static void main(String[] args) {

        var school = School.newBuilder().build();

        log.info("{}", school.getId()); // print 0;
        log.info("{}", school.getName()); // print nothing. Doesn't throw null pointer exception
        log.info("{}", school.getAddress().getCity()); // print nothing. Doesn't throw any null pointer exception

        log.info("is default ?: {} ", school.getAddress().equals(Address.getDefaultInstance()));

        log.info("Has address ?: {}", school.hasAddress());

        var lib  = Library.newBuilder().build();

        log.info("book list: {}", lib.getBooksList());

        var dealer = Dealer.newBuilder().build();
        log.info("inventory map: {}", dealer.getInventoryMap());

        var car = Car.newBuilder().build();
        log.info("{}", car.getBodyStyle());

    }
}
