package hu.webuni.logistics.dobiasz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.logistics.dobiasz.service.InitDBService;

@SpringBootApplication
public class LogisticsApplication implements CommandLineRunner {
	
	@Autowired
	InitDBService initDBService;
	
	@Autowired
	LogisticsApplication logisticsApplication;

	
	public static void main(String[] args) {
		SpringApplication.run(LogisticsApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		if(logisticsApplication != null) {
			initDBService.init();
		}
	}
	

}
