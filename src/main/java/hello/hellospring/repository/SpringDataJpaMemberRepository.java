package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// interface 가 interace를 받을때는 extends, java class 가 interface 를 받을때는 implements
// interface 는 다중 상속이 가능

// (이렇게 interface 만 있으면) SpringDataJpaMemberRepository 가 JpaRepository 를 받고 있으면 알아서
// interface 에 대한 구현체를 만들고 스프링 빈에 등록함
// 이 자동으로 등록된 스프링 빈을 SpringConfig 에서 injection 받아 사용하면됨
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { // <domain type, PK type

    // SpringDataJpaMemberRepository 에서 기본적인 CRUD 와 공통된 메소드는 JpaRepository 에 미리 구현이 되어있어서 가져다가 쓰면 되지만
    // (Repository <- CrudRepository <- PagingAndSortingRepository <- JpaRepository)
    // 비즈니스로직마다 다를 수 있는 부분은 공통 클래스로 제공되어있지 않아서 직접 선언해줘야함
    // ex) 주문서 번호로 조회해, 고객이 주문한 상품 번호로 조회해, name 으로 조회해, username 으로 조회해 등등..
    @Override
    Optional<Member> findByName(String name);
    // 제공되어있지 않은 메소드의 경우 이런식으로 적어두면
    // SpringDataJpaMemberRepository 가
    // select m from m where m.name = ?
    // 이라고 jpql 이 작성되서 sql 로 번역되어 실행됨

    /*
    위와 같은 원리로
    Optional<Member> findByNameAndId(String name, Long id)  // 선언
    -> select m from m where m.name = ? and m.id =?  // SpringDataJpaRepository 가 jpql 작성
    도 가능

    And, Or 등등...도 가능
     */

    /** SpringDataJpa 를 사용하면 -> 메소드 이름 만으로도 개발이 가능해진다 !! **/
}
