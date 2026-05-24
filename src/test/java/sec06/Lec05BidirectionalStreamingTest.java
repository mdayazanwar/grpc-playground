package sec06;

import com.ayaz.models.sec06.TransferRequest;
import com.ayaz.models.sec06.TransferResponse;
import com.ayaz.models.sec06.TransferStatus;
import common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Lec05BidirectionalStreamingTest  extends AbstractTest{

    @Test
    public void transfer() {
        var responseObserver = ResponseObserver.<TransferResponse>create();
        var requestObserver = this.transferStub.transfer(responseObserver);

        var requests = List.of(
                TransferRequest.newBuilder().setFromAccount(6).setToAccount(6).setAmount(10).build(),
                TransferRequest.newBuilder().setFromAccount(6).setToAccount(6).setAmount(110).build(),
                TransferRequest.newBuilder().setFromAccount(6).setToAccount(7).setAmount(10).build(),
                TransferRequest.newBuilder().setFromAccount(7).setToAccount(6).setAmount(10).build()

        );

        requests.forEach(requestObserver::onNext);
        requestObserver.onCompleted();
        responseObserver.await();

        Assertions.assertEquals(4, responseObserver.getItems().size());
        validate(responseObserver.getItems().get(0), TransferStatus.REJECTED, 100, 100);
        validate(responseObserver.getItems().get(1), TransferStatus.REJECTED, 100, 100);
        validate(responseObserver.getItems().get(2), TransferStatus.COMPLETED, 90, 110);
        validate(responseObserver.getItems().get(3), TransferStatus.COMPLETED, 100, 100);

    }

    private void validate(TransferResponse response, TransferStatus status, int fromAccountBalance, int toAccountBalance) {
        Assertions.assertEquals(status, response.getStatus());
        Assertions.assertEquals(fromAccountBalance, response.getFromAccount().getBalance());
        Assertions.assertEquals(toAccountBalance, response.getToAccount().getBalance());

    }
}
