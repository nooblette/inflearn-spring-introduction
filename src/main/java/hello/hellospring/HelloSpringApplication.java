package hello.hellospring;
// hello.hellospring 를 포함한 하위에서 스프링이 전부 뒤져서
// Component, Service, Controller, Repository Annotation이 달린 애를 스프링 빈으로 등록
// -> Component Scan

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {  // main method 실행
		SpringApplication.run(HelloSpringApplication.class, args);
		// 내장된 tomcat (웹 서버 구동)을 실행하며 spring boot application 실행
	}

}
