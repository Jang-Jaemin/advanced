package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {
    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId) {

        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };
        return template.execute("OrderController.request()");
   }
}

//  AbstractTemplate<String>
    //  제네릭을 String 으로설정했다. 따라서 AbstractTemplate 의 반환타입은 String 이 된다.
//  익명내부클래스
    //  익명내부클래스를사용한다. 객체를생성하면서 AbstractTemplate 를상속받은 자식클래스를 정의했다.
    //  따라서별도의자식클래스를 직접만들지않아도 된다.
//  template.execute("OrderController.request()")
    //  템플릿을실행하면서 로그로남길 message 를전달한다.
