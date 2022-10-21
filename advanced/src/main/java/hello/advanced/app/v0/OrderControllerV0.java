package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV0 {

    private final OrderServiceV0 orderService;

   @GetMapping("/v0/request")
    public String request(String itemId) {
       orderService.orderItem(itemId);
        return "ok";
   }
}

//  @RestController: 컴포넌트스캔과스프링 Rest 컨트롤러로인식된다.
//  /v0/request 메서드는 HTTP 파라미터로itemId를받을수있다.

// 실무에서는 일반적으로 사용하는 순서는
//  컨트롤로 -> 서비스 -> 리포지토리의 기본 흐름을 이용한다.