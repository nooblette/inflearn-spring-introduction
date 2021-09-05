package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration  //1. 스프링이 이 configuration 을 읽고
public class SpringConfig {

    /*
    private DataSource dataSource; // DB와 연결할 수 있는 정보가 있는 데이터소스 (JDBC)

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
     */

    /*
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){ // DB와 연결할 수 있는 정보가 있는 entity manager (Jpa)
        this.em = em;
    }
     */

    @Autowired
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // SpringDataJpa 1. SpringDataJpa 가 만든 구현체가 스프링 빈에 등록되어있고
   }

    @Bean  // 2. spring bean 에 등록해야하는것을 인식
    public MemberService memberService(){  // 3. 이 로직을 호출해서 Spring bean 에 등록
        return new MemberService(memberRepository);  //  4. memberService 가 spring bean 에 등록됨
        // SpringDataJpa 2. SpringDataJpa 가 알아서 만든 구현체를 그냥 injection 받아 등록하면됨
    }

//    @Bean // AOP 에 ComponentScan 어노테이션을 달아도 되는데, 이렇게 스프링 빈에 등록해서 사용하는걸 선호(AOP 를 걸었다는걸 인지할 수 있도록)
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    } // 여기서는 그냥 Component scan 을 사용

//    @Bean
//    public MemberRepository memberRepository(){
//        // 나중에 데이터베이스가 선정되면 다른 코드 수정없이 이 스프링 빈만 수정하면됨,
//        // MemoryMemberRepository -> DBMemberRepository
//        // 주로 컴포넌트 스캔을 사용하되, 상황에 따라 구현 클래스를 변경해야하면 설정을 통해 스프링 빈으로 등록한다.
//        return new MemoryMemberRepository(); // interface 가 아니라 구현체를 리턴
//    }


//    @Bean
//    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(datasource);
//        return new JdbcTemplateMemberRepository(datasource);
//        return new JpaMemberRepository(em);
//    }
}
