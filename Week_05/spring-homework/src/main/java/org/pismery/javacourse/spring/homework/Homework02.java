package org.pismery.javacourse.spring.homework;

import org.javacourse.school.autoconfiguration.bean.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Homework for spring bean auto configuration
 */
@SpringBootApplication
public class Homework02 implements ApplicationRunner{
	@Autowired
	private School school;

	public static void main(String[] args) {
		SpringApplication.run(Homework02.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(school);
	}
}
