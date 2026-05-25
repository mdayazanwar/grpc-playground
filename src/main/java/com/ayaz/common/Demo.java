package com.ayaz.common;

import com.ayaz.sec06.TransferService;
import com.ayaz.sec07.FlowControlService;
import com.ayaz.sec10.BankService;

public class Demo {

    public static void main(String[] args) {
       /* GrpcServer.create(new BankService(), new TransferService(), new FlowControlService())
                .start()
                .await();*/

        GrpcServer.create(new BankService())
                .start()
                .await();
    }
}
