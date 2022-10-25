package hello.advanced.app.v2;


import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;

        try {
            status = trace.beginSync(traceId,"OrderService.orderItem()");             orderRepository.save(itemId);
            trace.end(status.getTraceId(), status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}

//  orderItem()은파라미터로전달받은traceId를사용해서trace.beginSync()를실행한다.
//  beginSync()는내부에서다음traceId를생성하면서트랜잭션ID는유지하고level은하나 증가시킨다.
//  beginSync()가반환한새로운TraceStatus를orderRepository.save()를호출하면서파라미터로 전달한다.
//  TraceId를파라미터로전달하기위해orderRepository.save()의파라미터에TraceId를추가해야 한다.