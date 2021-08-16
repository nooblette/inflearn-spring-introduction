package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {  // main method 실행
		SpringApplication.run(HelloSpringApplication.class, args);
		// 내장된 tomcat (웹 서버 구동)을 실행하며 spring boot application 실행
	}

}
