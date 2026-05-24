package com.ayaz.common;

import com.ayaz.sec06.BankService;
import com.ayaz.sec06.TransferService;
import com.ayaz.sec07.FlowControlService;

public class Demo {

    public static void main(String[] args) {
        GrpcServer.create(new BankService(), new TransferService(), new FlowControlService())
                .start()
                .await();
    }
}
