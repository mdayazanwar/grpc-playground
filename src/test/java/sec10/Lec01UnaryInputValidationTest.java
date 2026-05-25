package sec10;

import com.ayaz.models.sec10.AccountBalance;
import com.ayaz.models.sec10.BalanceCheckRequest;
import com.ayaz.models.sec10.ValidationCode;
import common.ResponseObserver;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01UnaryInputValidationTest extends AbstractTest {

    private static final Logger log  = LoggerFactory.getLogger(Lec01UnaryInputValidationTest.class);

    @Test
    public void blockingInputValidationTest() {

        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(11)
                    .build();
                var response = this.bankBlockingStub.getAccountBalance(request);

        });

        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT, getValidationCode(ex));



    }

    @Test
    public void asyncInputValidationTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(11)
                .build();
        var responseObserver = ResponseObserver.<AccountBalance>create();
        this.bankStub.getAccountBalance(request, responseObserver);
        responseObserver.await();

        Assertions.assertTrue(responseObserver.getItems().isEmpty());
        Assertions.assertNotNull(responseObserver.getThrowable());
        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT, getValidationCode(responseObserver.getThrowable()));
    }
}
