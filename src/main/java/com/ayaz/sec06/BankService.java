package com.ayaz.sec06;

import com.ayaz.models.sec06.AccountBalance;
import com.ayaz.models.sec06.BalanceCheckRequest;
import com.ayaz.models.sec06.BankServiceGrpc;
import com.ayaz.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {

        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance =  AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();

    }
}
