package com.gi.task.store;

import com.gi.task.interfaces.BookStoreInterface;

public class BookStoreFactory {
	public static BookStoreInterface getBookStoreManager() {
		return new BookStoreManager();
	}
}
