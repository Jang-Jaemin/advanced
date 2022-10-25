/**
 *  로그추적기 V2 - 파라미터로동기화개발
 *  트랜잭션ID와메서드호출의깊이를표현하는하는가장단순한방법은
 *  첫로그에서사용한트랜잭션ID와 level을다음로그에넘겨주면된다.
 *  현재 로그의 상태 정보인 트랜잭션 ID와level은 TraceId에 포함 되어있다.
 *  따라서 TraceId를 다음 로그에 넘겨주면 된다.
 * **/



package hello.advanced.trace.hellotrace;


import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    public TraceStatus begin(String message) {

        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    //  V2에 추가 .
    //  HelloTraceV2는기존코드인HelloTraceV2와같고, beginSync(..)가추가되었다.
    /**
     * beginSync(..)
     * 기존TraceId에서createNextId()를통해다음 ID를구한다.
     * createNextId()의TraceId 생성로직은다음과같다.
     * 트랜잭션ID는기존과같이유지한다.
     * 깊이를표현하는 Level은하나증가한다. ( 0 -> 1)
     *
     * **/
    public TraceStatus beginSync(TraceId beforeTraceId, String message) {
        TraceId nextId = beforeTraceId.createNextId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[" + nextId.getId() + "] " + addSpace(START_PREFIX, nextId.getLevel()) + message);
        return new TraceStatus(nextId, startTimeMs, message);
    }

    public void end(TraceStatus status) {

        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }
    private void complete(TraceStatus status, Exception e) {

        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }

    public void end(TraceId traceId, TraceStatus status) {

    }
}

