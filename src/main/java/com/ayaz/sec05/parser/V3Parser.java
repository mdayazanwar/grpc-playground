package com.ayaz.sec05.parser;

import com.ayaz.models.sec05.v2.Television;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V3Parser {

    private static final Logger log  = LoggerFactory.getLogger(V3Parser.class);

    public static void parser(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);

        log.info("brand : {}", tv.getBrand());
        log.info("type: {}", tv.getType());
    }

}
