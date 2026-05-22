package com.ayaz.sec05;


import com.ayaz.models.sec05.v2.Television;
import com.ayaz.models.sec05.v2.Type;
import com.ayaz.sec05.parser.V1Parser;
import com.ayaz.sec05.parser.V2Parser;
import com.ayaz.sec05.parser.V3Parser;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V2VersionCompatibility {

    private static final Logger log  = LoggerFactory.getLogger(V2VersionCompatibility.class);

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("Samsung")
                .setModel(2019)
                .setType(Type.UHD)
                .build();

        V1Parser.parser(tv.toByteArray()); // Still works fine because when encoding works it doesn't consider the field name but the constant value
        V2Parser.parser(tv.toByteArray());
        V3Parser.parser(tv.toByteArray());

    }
}
