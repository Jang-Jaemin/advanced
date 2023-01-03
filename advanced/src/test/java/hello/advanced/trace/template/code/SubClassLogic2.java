package hello.advanced.trace.template.code;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubClassLogic2 extends AbstractTemplate {
    @Override
    protected void call(){
        log.info("비즈니스 로직2 실행");
    }
}

//  변하는부분인 비즈니스로직2를 처리하는자식클래스이다. 템플릿이호출하는 대상인 call() 메서드를
//  오버라이딩한다.