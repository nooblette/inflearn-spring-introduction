package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration  //1. 스프링이 이 configuration 을 읽고
public class SpringConfig {

    private DataSource dataSource; // DB와 연결할 수 있는 정보가 있는 데이터소스

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean  // 2. spring bean 에 등록해야하는것을 인식
    public MemberService memberService(){  // 3. 이 로직을 호출해서 Spring bean 에 등록
        return new MemberService(memberRepository());  //  4. memberService 가 spring bean 에 등록됨
    }


//    @Bean
//    public MemberRepository memberRepository(){
//        // 나중에 데이터베이스가 선정되면 다른 코드 수정없이 이 스프링 빈만 수정하면됨,
//        // MemoryMemberRepository -> DBMemberRepository
//        // 주로 컴포넌트 스캔을 사용하되, 상황에 따라 구현 클래스를 변경해야하면 설정을 통해 스프링 빈으로 등록한다.
//        return new MemoryMemberRepository(); // interface 가 아니라 구현체를 리턴
//    }


    @Bean
    public MemberRepository memberRepository(){
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
