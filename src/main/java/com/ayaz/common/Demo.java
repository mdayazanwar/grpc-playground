package com.ayaz.common;

import com.ayaz.sec06.BankService;
import com.ayaz.sec06.TransferService;

public class Demo {

    public static void main(String[] args) {
        GrpcServer.create(new BankService(), new TransferService())
                .start()
                .await();
    }
}
