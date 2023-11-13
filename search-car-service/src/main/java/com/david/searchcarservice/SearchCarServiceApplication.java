package com.david.searchcarservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SearchCarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchCarServiceApplication.class, args);
	}

}
