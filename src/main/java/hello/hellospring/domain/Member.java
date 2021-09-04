package hello.hellospring.domain;


//jpa 를 쓰려면 entity 를 mapping 해줘야함
//jpa -> interface 만을 제공(자바 진영 인터페이스), hibernates -> 구현체
//jpa -> ORM(object relation table Mpping 기술

import jdk.jfr.Enabled;

import javax.persistence.*;

@Entity //이 Member 는 jpa 가 관리하는 entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Id : id는 PK임을 표시, @GenerateValue, Identity : id 값을 디비가 알아서 생성
    private Long id;

    // @Column(name = "username") // 디비의 칼럼명 username 과 name 변수를 mapping
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
