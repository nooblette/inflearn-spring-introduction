package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){ // 각 method 에 대한 Test 를 실행하기 전에 아래 두 줄을 실행

        // MemberService 와 MemberServiceTest 가 동일한 인스턴스(MemoryMemberRepository)를 사용하기 위함
        // 쉽게 말해, 서로 다른 인스턴스를 사용하면 다른 DB를 사용하게 되므로 문제가 발생
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore(); // test 를 돌때마다 각 test 가 끝나면 Memory 를 clear 해줌
    }

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
        member.setName("Spring");

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

//        try {
//            memberService.join(member2);
//            fail(); // 의도적으로 예외를 발생시키게 했는데 그걸 못 잡는 경우 대비
//
//        } catch(IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}