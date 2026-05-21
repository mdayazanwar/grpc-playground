package com.ayaz.sec03;

import com.ayaz.models.sec03.Credentials;
import com.ayaz.models.sec03.Email;
import com.ayaz.models.sec03.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08OneOf {

    private  static final Logger log  = LoggerFactory.getLogger(Lec08OneOf.class);

    public static void main(String[] args) {

        var email = Email.newBuilder()
                .setAddress("test@gmail.com")
                .setPassword("admin")
                .build();

        var phone = Phone.newBuilder()
                .setNumber(1234567890)
                .setCode(123)
                .build();

        login(Credentials.newBuilder().setEmail(email).build());
        login(Credentials.newBuilder().setPhone(phone).build());
        login(Credentials.newBuilder().setEmail(email).setPhone(phone).build()); // In this case it will print phone as phone has been set last and it will override preset set operation

    }

    private  static  void login(Credentials credentials){
        switch (credentials.getLoginTypeCase()) {
            case EMAIL -> log.info("Email :{}", credentials.getEmail());
            case PHONE -> log.info("Phone :{}", credentials.getPhone());
        }
    }
}
