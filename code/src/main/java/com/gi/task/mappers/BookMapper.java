package com.gi.task.mappers;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import com.gi.task.exceptions.ApplicationException;
import com.gi.task.models.Book;
//Book mapper is responsible for transform book into many different shapes
public class BookMapper {
	private static String boundary = "=boundary=";

	public static Book getBookFromLine(String line) {
		line = new String(Base64.getDecoder().decode(line.getBytes()));
		String[] split = line.split(boundary);
		if (split.length != 4) {
			throw new ApplicationException("unexpected data for record " + line);
		}
		Integer id = Integer.parseInt(split[0]);
		return new Book(id, split[1], split[2], split[3]);
	}

	public static HashMap<Integer, Book> getBooksFromFile(List<String> fileLines) {
		HashMap<Integer, Book> books = new HashMap<>();
		if (fileLines != null) {
			fileLines.stream().map(x -> getBookFromLine(x)).forEach(book -> books.put(book.getId(), book));
		}
		return books;
	}

	public static String getBookString(Book book) {
		StringBuilder sb = new StringBuilder();
		sb.append(book.getId()).append(boundary).append(book.getTitle()).append(boundary).append(book.getAuthor())
				.append(boundary).append(book.getDescription());
		return new String(Base64.getEncoder().encode(sb.toString().getBytes()));
	}

	public static String getBookForMenuView(Book book) {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: ").append(book.getId()).append("\nTitle: ").append(book.getTitle()).append("\nAuthor: ")
				.append(book.getAuthor()).append("\nDescription: ").append(book.getDescription());
		return sb.toString();
	}

}
