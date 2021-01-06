package com.example.demo.exceptions;

public class CategoryNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(Long id) {
        super("No item with id: " + id);
    }
}
