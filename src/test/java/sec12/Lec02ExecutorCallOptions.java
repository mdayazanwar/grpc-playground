package sec12;

import com.ayaz.models.sec12.Money;
import com.ayaz.models.sec12.WithdrawRequest;
import common.ResponseObserver;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class Lec02ExecutorCallOptions extends  AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(Lec02ExecutorCallOptions.class);

    @Test
    public void executorDemo() {

        var observer = ResponseObserver.<Money>create();
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(20)
                .build();

        this.bankStub
                .withExecutor(Executors.newVirtualThreadPerTaskExecutor())
                .withdraw(request, observer);
        observer.await();
    }
}
