package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }
    }
}

//  설명 :
//  1. HelloTraceV1 trace: HelloTraceV1을주입받는다.
//  참고로HelloTraceV1은@Component 애노테이션을가지고있기때문에컴포넌트스캔의대상이된다.
//  따라서자동으로스프링빈으로등록된다.

//  2. trace.begin("OrderController.request()"): 로그를시작할때메시지이름으로컨트롤러이름 + 메서드이름을주었다.
//  이렇게하면어떤컨트롤러와메서드가호출되었는지로그로편리하게확인할수 있다.

//  3. 단순하게trace.begin(), trace.end() 코드두줄만적용하면될줄알았지만, 실상은그렇지않다.
//  trace.exception()으로예외까지처리해야하므로지저분한try, catch 코드가추가된다

//  4. begin()의결과값으로받은TraceStatus status 값을end(), exception()에넘겨야한다.  결국 try, catch 블록모두에이값을넘겨야한다.
//  따라서try 상위에TraceStatus status 코드를 선언해야한다.
//  만약try 안에서TraceStatus status를선언하면try 블록안에서만해당변수가 유효하기때문에catch 블록에넘길수없다. 따라서컴파일오류가발생한다.

//  5. throw e: 예외를꼭다시던져주어야한다. 그렇지않으면여기서예외를먹어버리고, 이후에정상 흐름으로동작한다.
//  로그는애플리케이션에흐름에영향을주면안된다. 로그때문에예외가사라지면 안된다.

