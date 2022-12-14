package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    private TraceId traceIdHolder; //traceId 동기화, 동시성 이슈 발생
    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
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
        releaseTraceId();
    }

    private void syncTraceId() {
        if (traceIdHolder == null) {
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) {
            traceIdHolder = null; //destroy
        } else {
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    private static String addSpace(String prefix, int level) {         StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}

//  FieldLogTrace는 기존에 만들었던 HelloTraceV2와거의같은기능을한다.
//  TraceId를 동기화 하는 부분만 파라미터를 사용하는것에서 TraceId traceIdHolder 필드를 사용하도록변경되었다.
//  이제직전로그의TraceId는 파라미터로 전달되는것이 아니라 FieldLogTrace의 필드인 traceIdHolder에 저장된다.

//  여기서 중요한 부분은 로그를 시작할 때 호출하는 syncTraceId()와 로그를 종료할때 호출하는 releaseTraceId()이다.
//  syncTraceId()
//  TraceId를 새로 만들거나 앞선 로그의TraceId를 참고해서 동기화하고, level도 증가한다. 최초호출이면TraceId를새로만든다.
//  직전로그가있으면해당로그의TraceId를 참고해서 동기화 하고, level도 하나 증가한다. 결과를traceIdHolder에보관한다.
//  releaseTraceId()
//  메서드를추가로호출할때는level이하나증가해야하지만, 메서드호출이끝나면level이하나 감소해야한다.
//  releaseTraceId()는level을하나감소한다.
//  만약최초호출( level==0)이면내부에서관리하는traceId를제거한다.