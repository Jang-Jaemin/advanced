package hello.advanced.trace;

public class TraceStatus {
    private TraceId traceId;
    private Long startTimeMS;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMS, String message){
        this.traceId = traceId;
        this.message = message;
        this.startTimeMS = startTimeMS;
    }

    public Long getStartTimeMS(){
        return startTimeMS;
    }

    public String getMessage() {
        return message;
    }

    public TraceId getTraceId() {
        return traceId;
    }


}

//  TraceStatus 클래스: 로그의상태정보를나타낸다.
//  TraceStatus는로그를시작할때의상태정보를가지고있다. 이상태정보는로그를종료할때사용된다.

//  traceId: 내부에트랜잭션ID와 level을가지고있다.
//  startTimeMs: 로그시작시간이다. 로그종료시이시작시간을기준으로시작~종료까지전체수행 시간을구할수있다.
//  message: 시작시사용한메시지이다. 이후로그종료시에도이메시지를사용해서출력한다.