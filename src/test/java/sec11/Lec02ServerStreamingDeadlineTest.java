package sec11;

import com.ayaz.models.sec11.Money;
import com.ayaz.models.sec11.WithdrawRequest;
import common.ResponseObserver;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec02ServerStreamingDeadlineTest extends  AbstractTest{

    private static final Logger log  = LoggerFactory.getLogger(Lec02ServerStreamingDeadlineTest.class);

    @Test
    public void blockingDeadlineTest() {

        var ex= Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = WithdrawRequest.newBuilder()
                    .setAccountNumber(1)
                    .setAmount(50)
                    .build();
            var iterator = this.bankBlockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .withdraw(request);
            while (iterator.hasNext()) {
                log.info("{}", iterator.next());
            }
        });

        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, ex.getStatus().getCode());
    }

    @Test
    public void AsyncDeadLineTest() {
        var observer = ResponseObserver.<Money>create();
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();

        this.bankStub
                .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                .withdraw(request, observer);
        observer.await();

        Assertions.assertEquals(2, observer.getItems().size());
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, Status.fromThrowable(observer.getThrowable()).getCode());
    }
}
