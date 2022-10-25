package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

        private final HelloTraceV2 trace; public void save(String itemId) {
        TraceStatus status = null;

        try {
            status = trace.beginSync(new TraceId(), "OrderRepository.save()");
        //저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//  save()는파라미터로전달받은traceId를사용해서trace.beginSync()를실행한다.
//  beginSync()는내부에서다음traceId를생성하면서트랜잭션ID는유지하고level은하나 증가시킨다.
//  beginSync()는이렇게갱신된traceId로새로운TraceStatus를반환한다.
//  trace.end(status)를호출하면서반환된TraceStatus를전달한다.