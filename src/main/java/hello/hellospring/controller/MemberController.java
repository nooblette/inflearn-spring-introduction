package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
// controller 는 스프링이 관리해야하기 때문에
// 스프링빈을 두는게 아니라 컴포넌트 스캔으로 스프링 컨테이너에 등록됨
/**
 * Controller Annotation 을 붙이면
 * 스프링 컨테이너에 이 MemberController 객체를 생성해서 넣어두고 스프링이 관리함, 관련된 기능들이 동작
 * 스프링 부트 안에 내장 톰캣서버, 스프링 컨테이너가 존재
 *
 * Controller 에는 @Controller 어노테이션
 * Service 에는 @Service 어노테이션
 * Repository 에는 @Repository 어노테이션을 붙여서 스프링이 관리하도록
 *
 * Controller 를 통해서 외부 요청을 받고
 * Service 에서 비즈니스 로직을 만들고
 * Repository 에서 데이터를 저장
 * 하는게 정형화된 패턴
 *
 **/
public class MemberController {

    // private final MemberService memberService = new MemberService();
    // 매번 MemberService 객체를 용로 선언할게 아니라 스프링 컨테이너에 하나만 등록해서 사용

    private final MemberService memberService;

    @Autowired // 스프링이 스프링 컨테이너에 있는 memberService 를 가져와서 연결시켜줌
    /** 연결 시켜줄땐 생성자에서 Autowired 어노테이션 사용 **/
    public MemberController(MemberService memberService) { // 생성자로 memvberService 를 연결
        this.memberService = memberService; // 생성자 주입(그 외에 setter 주입, 필드주입이 있음 / 생성자 주입을 가장 권장)
        // 멤버컨트롤러가 생성될때 스프링 빈에 등록되어있는 멤버 서비스 객체를 가져와서 넣어줌 -> DI(의존성 주입)
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);  // 회원가입

        return "redirect:/"; // 회원가입이 끝나면 홈 화면으로 보냄
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberlist";
    }
}
