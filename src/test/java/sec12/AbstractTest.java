package sec12;

import com.ayaz.common.GrpcServer;
import com.ayaz.models.sec12.BankServiceGrpc;
import com.ayaz.sec12.BankService;
import common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class AbstractTest extends AbstractChannelTest {
    private final GrpcServer grpcServer = GrpcServer.create(new BankService());
    protected BankServiceGrpc.BankServiceStub bankStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankStub = BankServiceGrpc.newStub(channel);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
