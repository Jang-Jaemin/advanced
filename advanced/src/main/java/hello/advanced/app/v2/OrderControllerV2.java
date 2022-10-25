//  메서드호출의깊이를표현하고, HTTP 요청도구분해보자.
//  이렇게하려면처음로그를남기는OrderController.request()에서로그를남길때어떤깊이와어떤 트랜잭션 ID를사용했는지다음차례인OrderService.orderItem()에서로그를남기는시점에 알아야한다.
//  결국현재로그의상태정보인트랜잭션ID와level이다음으로전달되어야한다.
//  이정보는TraceStatus.traceId에담겨있다.
//  따라서traceId를컨트롤러에서서비스를호출할때 넘겨주면된다


package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }
    }
}

//  TraceStatus status = trace.begin()에서반환받은TraceStatus에는 트랜잭션ID와level 정보가있는TraceId가있다.
//  orderService.orderItem()을호출할때TraceId를파라미터로전달한다.
//  TraceId 를파라미터로전달하기위해OrderServiceV2.orderItem()의파라미터에TraceId 를 추가해야한다.