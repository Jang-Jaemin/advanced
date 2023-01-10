// 추상 탬플릿 메서드 패턴
package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {
    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace){
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
    protected abstract T call();
}

//  AbstractTemplate 은템플릿 메서드패턴에서부모클래스이고, 템플릿역할을 한다.
//  <T> 제네릭을 사용했다. 반환타입을 정의한다.
//  객체를생성할 때 내부에서사용할 LogTrace trace 를 전달받는다.
//  로그에출력할 message 를외부에서파라미터로 전달받는다.
//  템플릿코드 중간에 call() 메서드를통해서변하는 부분을처리한다.
//  abstract T call() 은변하는 부분을처리하는메서드이다.
//  이 부분은상속으로구현해야한다.



