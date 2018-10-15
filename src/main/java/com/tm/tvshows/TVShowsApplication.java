package com.tm.tvshows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TVShowsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TVShowsApplication.class, args);
	}
}
