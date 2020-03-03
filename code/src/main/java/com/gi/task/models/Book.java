package com.gi.task.models;
//Book entity class
public class Book {
	private Integer id;
	private String title;
	private String author;
	private String description;
	/*
	 * Boolean edited indicate if the line is modified in order to update the book
	 * line only in the file based storage instead of rewrite all the file
	 */
	private Boolean edited;

	public Book() {
		edited = false;
	}

	public Book(Integer id, String title, String autor, String description) {
		this();
		this.id = id;
		this.title = title;
		this.author = autor;
		this.description = description;
	}

	private void checkEdit(Object target, Object source) {
		if (!source.equals(target)) {
			edited = true;
		}
	}

	public Boolean isEdited() {
		return edited;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		checkEdit(this.id, id);
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		checkEdit(this.title, title);
		this.title = title;
	}

	public String getAuthor() {
		if (author==null||author.equals("null")) {
			return "NA";
		}
		return author;
	}

	public void setAuthor(String autor) {
		checkEdit(this.author, autor);
		this.author = autor;
	}

	public String getDescription() {
		if (description==null||description.equals("null")) {
			return "NA";
		}
		return description;
	}

	public void setDescription(String description) {
		checkEdit(this.description, description);
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id=").append(id).append(", title=").append(title).append(", autor=").append(author)
				.append(", description=").append(description);
		return builder.toString();
	}

	public static class BookBuilder {
		private Integer id;
		private String title;
		private String author;
		private String description;

		public BookBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public BookBuilder title(String title) {
			this.title = title;
			return this;
		}

		public BookBuilder author(String author) {
			this.author = author;
			return this;
		}

		public BookBuilder description(String description) {
			this.description = description;
			return this;
		}

		public Book build() {
			return new Book(id, title, author, description);
		}
	}

}
