package sec12;

import com.ayaz.common.GrpcServer;
import com.ayaz.models.sec12.BankServiceGrpc;
import com.ayaz.sec12.BankService;
import common.AbstractChannelTest;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractInterceptorTest {
    private final GrpcServer grpcServer = GrpcServer.create(new BankService());
    protected ManagedChannel channel;
    protected BankServiceGrpc.BankServiceStub bankStub;
    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;

    protected abstract List<ClientInterceptor> getClientInterceptor();


    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankStub = BankServiceGrpc.newStub(channel);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .intercept(getClientInterceptor())
                .build();
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
