package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId) {
        // 저장 로직
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(10000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//  @Repository: 컴포넌트스캔의대상이된다. 따라서스프링빈으로자동등록된다.
//  sleep(1000): 리포지토리는상품을저장하는데약 1초정도걸리는것으로가정하기위해 1초지연을 주었다.  (1000ms)
//  예외가발생하는상황도확인하기위해파라미터itemId의값이ex로넘어오면 IllegalStateException 예외가발생하도록했다

