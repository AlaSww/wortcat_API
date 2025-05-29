package com.Alasww.wortcat_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class WortcatApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WortcatApiApplication.class, args);
	}

}