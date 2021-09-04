package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //jpa 는 엔티티매니저로 모든게 동작

    public JpaMemberRepository(EntityManager em){ // 스프링부트가 만들어준 엔티티매니저를 injection 받음
        this.em = em; // 스프링 부트가 자동으로 entity manager 를 생성, 데이터베이스를 다 연결
        // 엔티티매니저가 내부적으로 데이터 소스를 가지고 디비랑 통신, 처리
    }

    @Override
    public Member save(Member member) {
        em.persist(member);  //jpa 가 insert 쿼리 만들어서 디비에 저장, id 값 도  정해줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // find 의 매개변수로 (조회할 타입, pk값)을 넘어주면 select 쿼리 만들어서 알아서 조회함
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    // pk 기반이 아닌 것들은 jpql 을 작성해야함
    @Override
    public Optional<Member> findByName(String name) {
        // name 은 식별자(pk)가 아니여서 jpql 이라는 언어로 쿼리를 작성
        // sql -> table 대상으로 쿼리를 날림, jpql -> 객체(member entity)를 대상으로 쿼리를 날림
        // jpql 을 던지면 sql 로 번역해서 디비에 쿼리를 날림

        // member entity 는 이미 디비에 맵핑이 되어있으니까 member 객체 자체를 select
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // member entity 는 이미 디비에 맵핑이 되어있으니까 member 객체 자체를 select
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }
}
