package com.ayaz.sec06;

import com.ayaz.models.sec06.AccountBalance;
import com.ayaz.models.sec06.AllAccountResponse;
import com.ayaz.models.sec06.BalanceCheckRequest;
import com.ayaz.models.sec06.BankServiceGrpc;
import com.ayaz.sec06.repository.AccountRepository;
import com.google.protobuf.Empty;
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

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountResponse> responseObserver) {
        var accounts = AccountRepository.getAllAccount()
                .entrySet()
                .stream()
                .map(e  -> AccountBalance.newBuilder().setAccountNumber(e.getKey()).setBalance(e.getValue()).build())
                .toList();

        var response = AllAccountResponse.newBuilder()
                .addAllAccounts(accounts)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
