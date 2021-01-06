package com.example.demo.exceptions;

import lombok.NonNull;

public class DoubleItemException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public DoubleItemException(Long id, @NonNull String name) {
        super("Item with name: " + name+ " exists already and it's id is: "+id);
    }
}