package com.qzqv.vkbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class VkBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(VkBotApplication.class, args);
	}
}
