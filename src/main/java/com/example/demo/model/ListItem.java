package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "item")
@JsonSerialize(using = ItemSerializer.class)
public class ListItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "itemId")
	private Long itemId;

	@Column(name = "taskName")
	@NonNull
	@NotEmpty
	private String taskName;

	@Column(name = "createdAt")
	private Date createdAt = new Date();

	@ManyToMany(targetEntity = ItemCategory.class, fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(name = "Listitem_Category", joinColumns = {
			@JoinColumn(name = "listitemId", referencedColumnName = "itemId") }, inverseJoinColumns = {
					@JoinColumn(name = "categoryId", referencedColumnName = "categoryId") })
	Set<ItemCategory> categories = new HashSet<>();

	// to be able to check for the same items a task is considered the same if it
	// has the same name and belongs to the same list of categories
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof ListItem))
			return false;
		ListItem item = (ListItem) o;
		return Objects.equals(this.taskName, item.taskName) && Objects.equals(this.categories, item.categories);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.taskName, this.categories);
	}

	@Override
	public String toString() {
		return "Item{" + "name='" + this.taskName + '\'' + ", categories='" + this.categories + '\'' + '}';
	}
}
