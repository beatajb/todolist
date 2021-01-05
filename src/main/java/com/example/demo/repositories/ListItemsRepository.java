package com.example.demo.repositories;

import com.example.demo.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ListItemRepository")
public interface ListItemsRepository extends JpaRepository<ListItem, Long> 
{
}
