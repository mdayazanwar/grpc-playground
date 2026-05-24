package com.ayaz.sec06;

import com.ayaz.models.sec06.TransferRequest;
import com.ayaz.models.sec06.TransferResponse;
import com.ayaz.models.sec06.TransferServiceGrpc;
import com.ayaz.sec06.requesthandlers.TransferRequestHandler;
import io.grpc.stub.StreamObserver;

public class TransferService  extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }
}
