package com.example.demosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DemoSpApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoSpApplication.class, args);
	}

	@Scheduled(cron = "0 0 * * * *")
	@CacheEvict(value = "photo")
	public void clearCash(){};
}
