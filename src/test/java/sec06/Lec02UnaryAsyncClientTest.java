package sec06;

import com.ayaz.models.sec06.AccountBalance;
import com.ayaz.models.sec06.AllAccountResponse;
import com.ayaz.models.sec06.BalanceCheckRequest;
import com.google.protobuf.Empty;
import common.ResponseObserver;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class Lec02UnaryAsyncClientTest extends AbstractTest{

    private static  final Logger log = LoggerFactory.getLogger(Lec02UnaryAsyncClientTest.class);

    @Test
    public void getBalanceTest(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.stub.getAccountBalance(request, observer);
        observer.await();
        Assertions.assertEquals(1, observer.getItems().size());
        Assertions.assertEquals(100, observer.getItems().getFirst().getBalance());
        Assertions.assertNull(observer.getThrowable());
    }


    @Test
    public void allAccountTest() {
        var observer =  ResponseObserver.<AllAccountResponse>create();
        this.stub.getAllAccounts(Empty.getDefaultInstance(), observer);
        observer.await();
        Assertions.assertEquals(1, observer.getItems().size());
        Assertions.assertEquals(10, observer.getItems().getFirst().getAccountsCount());
        Assertions.assertNull(observer.getThrowable());
    }
}
