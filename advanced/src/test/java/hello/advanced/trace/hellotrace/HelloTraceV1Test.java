package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class HelloTraceV1Test {

    @Test
    void begin_end(){
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception(){
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }
}

/** 온전한 테스트 코드가 아니라 테스트는 못 한다. 간단한 예시로 만든 코드..
 *
 *  begin_end() - 실행로그
 *  [41bbb3b7] hello
 *  [41bbb3b7] hello time=5ms
 *
 *  begin_exception() - 실행로그
 *  [898a3def] hello
 *  [898a3def] hello time=13ms ex=java.lang.IllegalStateException
 * **/