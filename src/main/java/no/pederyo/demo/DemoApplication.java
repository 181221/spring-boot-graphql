package no.pederyo.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	ApplicationRunner init(UserService userService) {
		return args -> {
			Stream.of(
					"javadevjournal:Java Dev Journal",
					"octocat:The Octocat",
					"guest:From Another Universe"
			).forEach( data -> {
				User u = new User();
				String[] fields = data.split(":");
				u.setLogin(fields[0]);
				u.setName(fields[1]);
				userService.saveUser(u);
			});
			userService.getAllUsers().forEach(System.out::println);
		};
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/").allowedOrigins("*");
			}
		};
	}
}
