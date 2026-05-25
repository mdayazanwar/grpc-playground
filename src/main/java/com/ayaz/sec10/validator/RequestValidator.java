package com.ayaz.sec10.validator;

import com.ayaz.models.sec10.ErrorMessage;
import com.ayaz.models.sec10.ValidationCode;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;

import java.util.Map;
import java.util.Optional;

public class RequestValidator {

    private static final  Metadata.Key<ErrorMessage> ERROR_MESSAGE_KEY = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());

    public static Optional<StatusRuntimeException> validateAccount(int accountNumber) {
        if(accountNumber > 0 && accountNumber < 11) {
            return Optional.empty();
        }
        return Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(toMetaData(ValidationCode.INVALID_ACCOUNT)));

    }

    public static Optional<StatusRuntimeException> validateAmountDivisibleBy10(int amount) {
        if(amount > 0 && amount % 10 == 0) {
            return Optional.empty();
        }
        return Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(toMetaData(ValidationCode.INVALID_AMOUNT)));
    }


    public static Optional<StatusRuntimeException> hasSufficientBalance(int amount, int balance) {
        if(amount <= balance) {
            return Optional.empty();
        }
        return Optional.of(Status.FAILED_PRECONDITION.asRuntimeException(toMetaData(ValidationCode.INSUFFICIENT_BALANCE)));
    }

    private static Metadata toMetaData(ValidationCode code) {
        var metadata = new Metadata();
         var value =  ErrorMessage.newBuilder()
                 .setValidationCode(code)
                 .build();
         metadata.put(ERROR_MESSAGE_KEY, value);
         var key = Metadata.Key.of("desc", Metadata.ASCII_STRING_MARSHALLER);
         metadata.put(key, code.toString());
         return metadata;
    }
}
