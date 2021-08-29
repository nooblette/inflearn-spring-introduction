
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

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired // Autowired 가 있으면,
    // memberRepository 를 내가 생성하는게 아니라
    // 스프링이 스프링 컨테이너에 있는 MemberRepository 를 넣어줌 (Dependency Injection (DI))
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
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
