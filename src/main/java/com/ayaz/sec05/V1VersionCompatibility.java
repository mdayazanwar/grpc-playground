package com.ayaz.sec05;

import com.ayaz.models.sec05.v1.Television;
import com.ayaz.sec05.parser.V1Parser;
import com.ayaz.sec05.parser.V2Parser;
import com.ayaz.sec05.parser.V3Parser;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1VersionCompatibility {

    private static final Logger  log  = LoggerFactory.getLogger(V1VersionCompatibility.class);

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("Samsung")
                .setYear(2019)
                .build();

        V1Parser.parser(tv.toByteArray());
        V2Parser.parser(tv.toByteArray());
        V3Parser.parser(tv.toByteArray());
    }
}
