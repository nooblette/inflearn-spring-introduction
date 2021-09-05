
/**
 * 원할한 유지보수와 커뮤니케이션을 위해,
 * Service Class 는 비즈니스에 가까운 용어를 써야함(비즈니스 의존성)
 *                      vs
 * Repositroy 는 더 개발스러운 용어들을 선택(데이터를 push, pop ...)
 *
 * 각자 역할에 맞게 네이밍 하는것이 중요!!
 */


package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// jpa 를 사용하려면 data 를 저장하거나 변경할때 항상 transactional 이 있어야함

// DB에 쿼리를 던진 후에 commit 을 해야 반영이 됨.
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // Autowired 가 있으면,
    // memberRepository 를 내가 생성하는게 아니라
    // 스프링이 스프링 컨테이너에 있는 MemberRepository 를 넣어줌 (Dependency Injection (DI))
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){

        //join method 가 걸리는 시간을 ms 로 측정

        /** AOP 를 사용하지 않으면 모든 메소드에 아래와 같이 try, finally 를 적용해야 하지만, **/
//        long start = System.currentTimeMillis();
//
//        try {
//            // 회원중에 동명이인이 있으면 안된다는 요구사항을 구현
//            validateDuplicateMember(member); // 중복회원 검증
//
//            memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }

        /** AOP 를 사용하면(AOP class 를 만들고 bean 에 등록하면) 아래처럼 핵심 로직만 넣으면 됨 **/

        // 회원가입, 회원조회 -> 핵심 관심사항, 시간 측정 -> 공통 관심사항
        // 따라서 시간을 측정하는 로직을 별도의 공통 로직으로 만듬(AOP)
        // 핵심 관심사항을 깔끔하게 유지할 수 있고, 변경이 필요하면 AOP 만 변경하면 된다.
        // 원하는 적용 대상을 선택, 조작 가능

        // 회원중에 동명이인이 있으면 안된다는 요구사항을 구현
        validateDuplicateMember(member); // 중복회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // result를 Optional로 감쌌기 때문에 Null이면 ifPresent로 처리가능
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 특 회원 조회
     */
    public Optional<Member> findOne(Long MemberId){
        return memberRepository.findById(MemberId);
    }
}
