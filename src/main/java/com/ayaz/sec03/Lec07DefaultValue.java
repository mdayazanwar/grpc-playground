package com.ayaz.sec03;

import com.ayaz.models.sec03.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07DefaultValue {

    private static final Logger log  = LoggerFactory.getLogger(Lec07DefaultValue.class);

    public static void main(String[] args) {

        var school = School.newBuilder().build();

        log.info("{}", school.getId()); // print 0;
        log.info("{}", school.getName());
    }
}
