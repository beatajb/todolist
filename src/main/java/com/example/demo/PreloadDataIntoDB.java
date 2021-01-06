package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.ItemCategory;
import com.example.demo.model.ListItem;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ListItemsRepository;

@Configuration
public class PreloadDataIntoDB {	

	  private static final Logger log = LoggerFactory.getLogger(PreloadDataIntoDB.class);

	  @Bean
	  CommandLineRunner initDatabase(ListItemsRepository repositoryList, CategoryRepository repositoryCategory) {

		  repositoryList.deleteAllInBatch();
	      repositoryCategory.deleteAllInBatch();
	    
	      return args -> {
	    	ListItem newItem = new ListItem("wash dishes");
	    	ListItem newItem2 = new ListItem("clean apartment");
	    	ListItem newItem3 = new ListItem("clean second apartment");
	    	ItemCategory category1 = new ItemCategory("Chore");
	    	ItemCategory category2 = new ItemCategory("Apartment");	    	
	    	newItem2.getCategories().add(category1);
	    	newItem3.getCategories().add(category1);
	    	newItem3.getCategories().add(category2);
	      log.info("Preloading " + repositoryList.save(newItem));
	      log.info("Preloading " + repositoryList.save(newItem2));
	      //log.info("Preloading " + repositoryList.save(newItem3));
		    
	    };
	  }
}
