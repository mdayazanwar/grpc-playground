package sec12;

import com.ayaz.models.sec12.Money;
import com.ayaz.models.sec12.WithdrawRequest;
import com.ayaz.models.sec12.AccountBalance;
import com.ayaz.models.sec12.BalanceCheckRequest;
import common.ResponseObserver;
import io.grpc.ClientInterceptor;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sec12.interceptor.DeadLineInterceptor;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec03DeadLineTest extends  AbstractInterceptorTest{

    @Override
    protected List<ClientInterceptor> getClientInterceptor() {
        return List.of(new DeadLineInterceptor(Duration.ofSeconds(2)));
    }

    @Test
    public  void blockingDeadlineTest() {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(1)
                    .build();

            var response = this.bankBlockingStub
                    .getAccountBalance(request);
    }

    @Test
    public void  asyncDeadLine() {
        var observer = ResponseObserver.<AccountBalance>create();
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();

        this.bankStub
                .getAccountBalance(request, observer);

        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, Status.fromThrowable(observer.getThrowable()).getCode());
    }

    @Test
    public void overrideInterceptorDemo() {
        var observer = ResponseObserver.<Money>create();
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();

        this.bankStub
                .withDeadline(Deadline.after(6, TimeUnit.SECONDS))
                .withdraw(request, observer);
        observer.await();
    }
}

