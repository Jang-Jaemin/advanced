package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private String id ;
    private int level;

    public TraceId(){
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level){
        this.id = id;
        this.level = level;
    }

    private String createId(){

        //  ab99e16f-3cde-4d24-8241-256108c203a2
        //  생성된 UUID ab99e16f //앞 8자리만 사용
        return UUID.randomUUID().toString().substring(0,8);
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId(){
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}

//  TraceId 클래스
//  로그추적기는트랜잭션ID와깊이를표현하는방법이필요하다.
//  여기서는트랜잭션ID와깊이를표현하는 level을묶어서TraceId라는개념을만들었다.
//  TraceId는단순히id(트랜잭션ID)와level정보를함께가지고있다.

//  UUID
//  TraceId를처음생성하면createId()를사용해서 UUID를만들어낸다.
//  UUID가너무길어서여기서는 앞 8자리만사용한다.
//  이정도면로그를충분히구분할수있다.
//  여기서는이렇게만들어진값을트랜잭션ID 로사용한다

//  createNextId()
//  다음TraceId를만든다.
//  예제로그를잘보면깊이가증가해도트랜잭션ID는같다. 대신에깊이가하나 증가한다.

//  createPreviousId()
//  createNextId()의반대역할을한다.
//  id는기존과같고, level은하나감소한다.