package com.ayaz.sec06.requesthandlers;

import com.ayaz.models.sec06.AccountBalance;
import com.ayaz.models.sec06.TransferRequest;
import com.ayaz.models.sec06.TransferResponse;
import com.ayaz.models.sec06.TransferStatus;
import com.ayaz.sec06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferRequestHandler implements StreamObserver<TransferRequest> {

    private static final Logger log = LoggerFactory.getLogger(TransferRequestHandler.class);
    private final StreamObserver<TransferResponse> responseObserver;

    public TransferRequestHandler(StreamObserver<TransferResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }


    @Override
    public void onNext(TransferRequest transferRequest) {
        var status = this.transfer(transferRequest);
         var response = TransferResponse.newBuilder()
                    .setFromAccount(this.toAccountBalance(transferRequest.getFromAccount()))
                    .setToAccount(this.toAccountBalance(transferRequest.getToAccount()))
                    .setStatus(status)
                    .build();

         this.responseObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("Client Error: {}", throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("Transfer Request stream completed");
        this.responseObserver.onCompleted();
    }

    private TransferStatus transfer(TransferRequest transferRequest) {
        var amount = transferRequest.getAmount();
        var fromAccount = transferRequest.getFromAccount();
        var toAccount = transferRequest.getToAccount();
        var transferStatus = TransferStatus.REJECTED;
        if (AccountRepository.getBalance(fromAccount) >= amount && (fromAccount != toAccount)) {
            AccountRepository.deductAmount(fromAccount, amount);
            AccountRepository.addAmount(toAccount, amount);
            transferStatus = TransferStatus.COMPLETED;
        }
        return transferStatus;
    }

    private AccountBalance toAccountBalance(int accountNumber) {
        return AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(accountNumber))
                .build();
    }

}
