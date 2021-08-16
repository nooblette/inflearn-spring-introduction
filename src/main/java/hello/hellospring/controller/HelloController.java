package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // controller 는 첫번째 진입
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {  // 스프링이 model을 만들어서 넣어줌
        model.addAttribute("data", "hello!!"); // data -> model에서 key값
        return "hello";  // resources/templates/hello(= View Name).html 을 찾아서 렌더링
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) { // 외부로부터 파라미터를(RequestParam) 받은 메소드
        /*
         * http://localhost:8080/hello-mvc?name=spring! 로 요청하면
         * name에 'spring!'이라는 값이 담김
         * name에 있는 값(=spring!)이 model에 담겨서 템플릿으로 넘어감
         *
         * 템플릿 파일(hello-template.html)에서
         * <p th:text="'hello ' + ${name}">hello! empty</p>
         * 부분의 hello! empty가 hello name 으로 치환됨(여기서는 hello spring!)
         */
        model.addAttribute("name", name); // name -> model에서 key값
        return "hello-template";
    }

    @GetMapping("hello-string")
    /*
    mvc 에서 View 를 찾아서 템픓릿 엔진을 통해 화면을 렌더링해서 html 을 웹 브라우저로 넘기기
    api 로 데이터를 바로 내리기

    두가지 방법이 존재
    */
    @ResponseBody // http의 응답 body부에 이 데이터를 직접 넣겠다(api를 통해 데이터를 바로 내리겠다).
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //hello spring
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;  // JSON 포맷으로 {"name":"spring!!!"} 리턴
    }

    static class Hello {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
