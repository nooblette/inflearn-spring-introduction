package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행
@Transactional
// DB에 쿼리를 던진 후에 commit 을 해야 반영이 됨.
// 테스트 케이스에선 Transactional Annotation 을 통해,
// 테스트 케이스의 트랜잭션을 실행하고 디비에 쿼리를 던지고 테스트가 끝나면 디비를 롤백함 (테스트시 디비에 넣은 데이터를 다 날려버림)
// 트랜잭션을 롤백함으로써(디비에 데이터가 반영x) 동일한 테스트를 반복해서 실행할 수 있다.

class MemberServiceIntegrationTest {
/** 보통 테스트 전용 DB를 별도로 구축해둠(혹은 local DB를 테스트용도로 사용) **/

    // 테스트 할때는 편하게 필드기반으로 의존성 주입받음
    @Autowired  MemberService memberService;
    @Autowired  MemberRepository memberRepository;

    @Test
    void 회원가입() { // 테스트 관련 코드들은 빌드될 때 실제 코드에 포함x 의사소통 하기 쉽게 그냥 한글로 작성해도됨

        /**
         * 테스트 기본 문법 Given-When-Then
         *
         * Given - 무언가가 주어진 상황에서
         * When - 무언가를 실행 했을 때
         * Then - 결과가 이게 나와야돼(검증부)
         */

        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("Spring");

        Member member2 = new Member();
        member2.setName("Spring");

        //when
        memberService.join(member1);

        /**
         * " () -> memberService.join(member2) " 로직을 실행할 건데
         * " IllegalStateException " <- 이 예외가 터지면 테스트 성공
         */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 에러 메시지 검증
    }
}