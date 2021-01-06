package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ItemCategory;

public interface CategoryRepository extends JpaRepository<ItemCategory, Long>{ 

}
