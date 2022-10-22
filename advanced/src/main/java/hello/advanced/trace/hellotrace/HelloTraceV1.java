package hello.advanced.trace.hellotrace;


import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    public TraceStatus begin(String message) {

        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
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
}

//  HelloTraceV1을사용해서실제로그를시작하고종료할수있다. 그리고로그를출력하고실행시간도 측정할수있다.
//  @Component: 싱글톤으로사용하기위해스프링빈으로등록한다. 컴포넌트스캔의대상이된다.

//  공개메서드
//  로그추적기에서사용되는공개메서드는다음 3가지이다.
//  begin(..)
//  end(..)
//  exception(..)

//  자세한 설명
//  TraceStatus begin(String message)
//  로그를시작한다.
//  로그메시지를파라미터로받아서시작로그를출력한다. 응답결과로현재로그의상태인TraceStatus를반환한다.

//  void end(TraceStatus status)
//  로그를정상종료한다.
//  파라미터로시작로그의상태( TraceStatus)를전달받는다. 이값을활용해서실행시간을계산하고, 종료시에도시작할때와동일한로그메시지를출력할수있다.
//  정상흐름에서호출한다.

//  void exception(TraceStatus status, Exception e)
//  로그를예외상황으로종료한다.
//  TraceStatus, Exception 정보를함께전달받아서실행시간, 예외정보를포함한결과로그를 출력한다.
//  예외가발생했을때호출한다.