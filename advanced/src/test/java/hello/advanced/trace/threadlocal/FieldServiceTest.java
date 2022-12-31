package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {
    private Process log;

    @Test 
    void field() {
        log.info("main start");
        FieldService.FieldService fieldService;
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };
        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start(); //A실행
        sleep(2000); //동시성 문제 발생X
//        sleep(100); //동시성 문제 발생O 
        threadB.start(); //B실행
        sleep(3000); //메인 쓰레드 종료 대기
        log.info("main exit");
    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//  설명
//  순서대로 실행
//  sleep(2000) 을설정해서 thread-A 의실행이끝나고 나서 thread-B 가 실행되도록해보자.
//  참고로 FieldService.logic() 메서드는내부에 sleep(1000) 으로 1초의지연이 있다.
//  따라서 1초 이후에호출하면 순서대로실행할수 있다. 여기서는넉넉하게 2초 (2000ms)를 설정했다.

//  동시성문제 발생 코드
//  이번에는 sleep(100) 을설정해서 thread-A 의작업이 끝나기전에 thread-B 가 실행되도록해보자.
//  참고로 FieldService.logic() 메서드는내부에 sleep(1000) 으로 1초의지연이 있다.
//  따라서 1초 이후에 호출하면 순서대로실행할수 있다.
//  다음에설정할 100(ms)는 0.1초이기 때문에 thread-A 의 작업이끝나기 전에 thread-B 가 실행된다.
