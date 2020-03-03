package com.gi.task.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.gi.task.exceptions.ApplicationException;
import com.gi.task.interfaces.BookStoreInterface;
import com.gi.task.interfaces.DataStorageStrategy;
import com.gi.task.models.Book;
import com.gi.task.storage.StorageFactory;
//BookStoreManager responsible for manipulate the library items
class BookStoreManager implements BookStoreInterface {
	private DataStorageStrategy dataStorageStrategy;
	private HashMap<Integer, Book> books;
	private AtomicInteger currentIdIndex;

	public BookStoreManager() {
		dataStorageStrategy = StorageFactory.getDataStorage("file");
		books = dataStorageStrategy.loadAll();
		currentIdIndex = new AtomicInteger(books.size());
	}

	@Override
	public List<Book> getAll() {
		return new ArrayList<Book>(books.values());
	}

	@Override
	public synchronized Book addBook(Book book) {
		book.setId(currentIdIndex.incrementAndGet());
		books.put(book.getId(), book);
		return book;
	}

	@Override
	public Book editBook(Book book) {
		Book tempBook = books.get(book.getId());
		if (tempBook == null) {
			throw new ApplicationException("Failed To Edit,Can't find book for id " + book.getId());
		}
		tempBook.setAuthor(book.getAuthor());
		tempBook.setTitle(book.getTitle());
		tempBook.setDescription(book.getDescription());
		return book;
	}

	@Override
	public Book findOne(Integer id) {
		return books.get(id);
	}

	@Override
	public List<Book> findByTitle(String title) {
		return books.values().stream().filter(x -> x.getTitle().toLowerCase().contains(title.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public void flushChanges() {
		boolean updateFile = false;
		for (Book book : books.values()) {
			if (book.isEdited()) {
				updateFile = true;
				break;
			}
		}
		if (updateFile) {
			dataStorageStrategy.flush(books);
		}
	}

}
