package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 0, 1, 2 등 ... key 값을 생성해주는 애

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // Optional.ofNullable() : store에서 id로 찾은애가 없어도(null이여도) 감싸서 반환
        // 이러면 클라이언트에서 무언가를 할 수 있음(나중에 설명)
    }

    @Override
    public Optional<Member> findByName(String name) {
        // java lambda 로 구현
        return store.values().stream() // loop를 돌림
                .filter(member-> member.getName().equals(name)) // member의 get name이 파라미터로 넘어온 name과 같은지 확인(필터링)
                .findAny(); // 하나라도 찾으면 결과를 Optional로 반환, 없으면 null을 감싸서 반환
        }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store의 valuse(member)를 ArrayList type으로 전부 다 반환
    }

    public void clearStore(){
        store.clear(); // store 을 싹 비움
    }
}
