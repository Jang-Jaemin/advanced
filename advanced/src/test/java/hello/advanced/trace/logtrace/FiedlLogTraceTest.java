package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class FiedlLogTraceTest {

        FieldLogTrace trace = new FieldLogTrace();

        @Test
        void begin_end_level2() {
            TraceStatus status1 = trace.begin("hello1");
            TraceStatus status2 = trace.begin("hello2");
            trace.end(status2);
            trace.end(status1);
        }

        @Test
        void begin_exception_level2() {
            TraceStatus status1 = trace.begin("hello");
            TraceStatus status2 = trace.begin("hello2");
            trace.exception(status2, new IllegalStateException());
            trace.exception(status1, new IllegalStateException());
        }
}


//  실행 결과는 =
//  [ed72b67d] hello1
//  [ed72b67d] |-->hello2
//  [ed72b67d] |<--hello2 time=2ms
//  [ed72b67d] hello1 time=6ms

//  begin_exception_level2() - 실행결과
//  [59770788] hello
//  [59770788] |-->hello2
//  [59770788] |<X-hello2 time=3ms ex=java.lang.IllegalStateException
//  [59770788] hello time=8ms ex=java.lang.IllegalStateException