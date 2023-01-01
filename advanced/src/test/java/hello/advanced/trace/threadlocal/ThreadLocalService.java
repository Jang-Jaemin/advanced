package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalService {

    private ThreadLocal<String> nameStore = new ThreadLocal<>();


    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}", name, nameStore.get());
        nameStore.set(name);
        sleep(1000);
        log.info("조회 nameStore={}", nameStore.get());
        return nameStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//  FieldService 와 거의같은코드인데,
//  nameStore 필드가일반 String 타입에서 ThreadLocal 을사용하도록 변경함.

// 주의
//  해당쓰레드가쓰레드 로컬을 모두사용하고 나면 ThreadLocal.remove() 를
//  호출해서 쓰레드로컬에 저장된값을 제거해주어야한다.
