package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

public class FieldService {
    @Slf4j
    public class FieldService {
        private String nameStore;

        public String logic(String name) {
            log.info("저장 name={} -> nameStore={}", name, nameStore);
            nameStore = name;
            sleep(1000);
            log.info("조회 nameStore={}", nameStore);
            return nameStore;
        }

        private void sleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//  매우단순한로직이다. 파라미터로넘어온 name 을 필드인 nameStore 에 저장한다.
//  그리고 1초간쉰 다음 필드에저장된 nameStore 를 반환한다.