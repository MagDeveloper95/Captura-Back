package com.iesFrancisco.captura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = AppMain.class)
public class AppMain {

	public static void main(String[] args) {
		
		SpringApplication.run(AppMain.class, args);
		
	}

}
