package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.ListItem;
import com.example.demo.repositories.ListItemsRepository;

@Configuration
public class PreloadDataIntoDB {	

	  private static final Logger log = LoggerFactory.getLogger(PreloadDataIntoDB.class);

	  @Bean
	  CommandLineRunner initDatabase(ListItemsRepository repositoryList) {

	    return args -> {
	      log.info("Preloading " + repositoryList.save(new ListItem("wash dishes")));
	      log.info("Preloading " + repositoryList.save(new ListItem("clean apartment")));
	    };
	  }
}
