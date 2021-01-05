package com.example.demo.exceptions;

public class ItemNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(Long id) {
        super("No item with id: " + id);
    }
}
