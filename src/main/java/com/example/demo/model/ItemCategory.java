package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor 
@NoArgsConstructor
@Table(name= "category")
public class ItemCategory {
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="categoryId")
    private Long categoryId;
	
	@Column(name="categoryName")
    @NonNull
    @NotEmpty
    private String categoryName;

    @Column(name="createdAt")
    private Date createdAt = new Date();
    
    @JsonIgnore
    @ManyToMany (fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "categories")
    private Set<ListItem> items = new HashSet<>(); 
    
    public void addListItem(ListItem item) {
        items.add(item);
        item.getCategories().add(this);
    }

    @Override
    public boolean equals(Object o) {

      if (this == o)
        return true;
      if (!(o instanceof ItemCategory))
        return false;
      ItemCategory item = (ItemCategory) o;
      return Objects.equals(this.categoryId, item.categoryId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.categoryId);
    }

    @Override
    public String toString() {
      return "Category{" + "id='" + this.categoryId + '\'' + '}';
    }
}
