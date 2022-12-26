package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class LogTraceConfig {

    @Configuration
    public class LogTraceConfig{

        @Bean
        public LogTrace logTrace(){
            return new FieldLogTrace();
        }
    }
}
