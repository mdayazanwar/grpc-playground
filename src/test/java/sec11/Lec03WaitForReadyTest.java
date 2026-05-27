package sec11;

import com.ayaz.common.GrpcServer;
import com.ayaz.models.sec11.BankServiceGrpc;
import com.ayaz.models.sec11.WithdrawRequest;
import com.google.common.util.concurrent.Uninterruptibles;
import common.AbstractChannelTest;
import io.grpc.Deadline;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec03WaitForReadyTest  extends AbstractChannelTest {

    private static final Logger log  = LoggerFactory.getLogger(Lec03WaitForReadyTest.class);
    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());
    private BankServiceGrpc.BankServiceStub bankStub;
    private BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;

    @BeforeAll
    public void setup() {
        //this.grpcServer.start();
        Runnable runnable = () -> {
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            this.grpcServer.start();
        };
        Thread.ofVirtual().start(runnable);
        this.bankStub = BankServiceGrpc.newStub(channel);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void blockingDeadlineTest() {
        log.info("Sending the Request");
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();
        var iterator = this.bankBlockingStub
                .withWaitForReady()
                .withDeadline(Deadline.after(15, TimeUnit.SECONDS))
                .withdraw(request);
        while (iterator.hasNext()) {
            log.info("{}", iterator.next());
        }

    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }
}
