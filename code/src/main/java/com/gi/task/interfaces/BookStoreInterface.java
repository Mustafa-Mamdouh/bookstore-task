package com.gi.task.interfaces;

import java.util.List;

import com.gi.task.models.Book;
//Interface for bookStore to provide inversion of control
public interface BookStoreInterface {
	// 1) View all books
	List<Book> getAll();

	// 2) Add a book
	Book addBook(Book book);

	// 3) Edit a book
	Book editBook(Book book);

	// 4) Search for a book
	Book findOne(Integer id);

	List<Book> findByTitle(String title);

	void flushChanges();
}
