// logtrace 인터페이스


package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}

//  LogTrace 인터페이스에는로그추적기를위한최소한의기능인begin(), end(), exception()를 정의했다.
//  이제파라미터를넘기지않고TraceId를동기화할수있는FieldLogTrace 구현체를만들어보자.

