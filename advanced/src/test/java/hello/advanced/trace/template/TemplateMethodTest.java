package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {
    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }
    private void logic1() {
        long startTime = System.currentTimeMillis();
//비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
//비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
    private void logic2(){
        long startTime = System.currentTimeMillis();
        long.info("비즈니스 로직 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();

    }

    @Test
    void templateMethodV2(){
        AbstractTemplate template = new AbstractTemplate(){
            @Override
            protected void call(){
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름1={}", template1.getClass());
        template.execute();

        AbstractTemplate template2 = new AbstractTemplate(){
            @Override
            protected void call(){
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름2={}", template2.getClass());
        template2.execute();
    }
}

//  logic1() , logic2() 를호출하는 단순한테스트코드이다.

//  실행 결과 :
/**  비즈니스 로직1 실행
     resultTime=5
     비즈니스 로직2 실행
     resultTime=1
 **/

//  변하는부분: 비즈니스 로직
//  변하지않는 부분: 시간측정

// 실행 결과 :
/** 클래스 이름1 class hello.advanced.trace.template.TemplateMethodTest$1
    비즈니스 로직1 실행
    resultTime=3
    클래스 이름2 class hello.advanced.trace.template.TemplateMethodTest$2
    비즈니스 로직2 실행
    resultTime=0
**/

//  실행결과를보면 자바가임의로만들어주는 익명내부클래스 이름은 TemplateMethodTest$1 ,
//  TemplateMethodTest$2 인것을확인할 수있다.



//  실행 결과 :
/**  비즈니스 로직1 실행
 resultTime=5
 비즈니스 로직2 실행
 resultTime=1
 **/

//  변하는부분: 비즈니스 로직
//  변하지않는 부분: 시간측정

// 실행 결과 :
/** 클래스 이름1 class hello.advanced.trace.template.TemplateMethodTest$1
 비즈니스 로직1 실행
 resultTime=3
 클래스 이름2 class hello.advanced.trace.template.TemplateMethodTest$2
 비즈니스 로직2 실행
 resultTime=0
 **/

//  실행결과를보면 자바가임의로만들어주는 익명내부클래스 이름은 TemplateMethodTest$1 ,
//  TemplateMethodTest$2 인것을확인할 수있다.