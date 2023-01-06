package hello.advanced.app.v4;


import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }
 }
//  AbstractTemplate<Void>
//  제네릭에서 반환타입이필요한데, 반환할내용이 없으면 Void 타입을사용하고 null 을반환하면 된다.
//  참고로제네릭은기본 타입인 void , int 등을선언할수 없다.