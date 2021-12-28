package com.test.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 注意包名、扫描包的范围，因代码从JFJDAG迁移包名不同
 */
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableAsync
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
