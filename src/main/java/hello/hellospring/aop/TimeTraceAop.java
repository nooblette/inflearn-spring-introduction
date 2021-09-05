package hello.hellospring.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP : Aspect Object Programming(관점지향 프로그래밍)
 * 공통 관심 사항 vs 핵심 관심 사항 분리
 * memberController -> memberService -> memberRepository 에서
 * TimeTraceAop(시간 측정 로직)을 원하는 곳에 공통 관심 사항 적용
 **/
@Aspect // AOP 적용
@Component
public class TimeTraceAop {

    // 이 공통 관심사항을 어느 곳(패키지 하위전부, 클래스명 등등.. 다 가능)에 적용할지 타겟팅
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString()); // 어떤 메소드를 호출했는지 출력
        try {
            return joinPoint.proceed(); // 메소드 실행하고 다음 메소드를 호출
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
