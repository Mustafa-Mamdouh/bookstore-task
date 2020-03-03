package com.gi.task.menus;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.gi.task.interfaces.BookStoreInterface;
import com.gi.task.mappers.BookMapper;
import com.gi.task.models.Book;
import com.gi.task.models.MenuData;
import com.gi.task.store.BookStoreFactory;

public class UserInterfaceMenuManager {
	Scanner scan;
	MenuData mainMenuData;

	public UserInterfaceMenuManager() {
		scan = new Scanner(System.in);
		String[] mainMenuItems = { "1) View all books", "2) Add a book", "3) Edit a book", "4) Search for a book",
				"5) Save and exit" };
		mainMenuData = new MenuData("==== Book Manager ====", "", null);
		int i = 0;
		for (String menuOption : mainMenuItems) {
			mainMenuData.addMenuOption(++i, menuOption);
		}
	}

	public int showMenu(MenuData menuData) {
		try {
			System.out.println(menuData.getHeader());
			Collection<String> options = menuData.getMenuItems().values();
			options.stream().forEach(System.out::println);
			System.out.println(menuData.getMenuMessage());
			String userStringInput = scan.nextLine();
			if ("".equals(userStringInput)) {
				return 0;
			}
			int userInput = Integer.parseInt(userStringInput);
			if (userInput > 0 && menuData.getMenuItems().keySet().contains(userInput)) {
				return userInput;
			} else {
				System.err.println("Invalid user input");
				return showMenu(menuData);
			}
		} catch (Exception e) {
			System.err.println("Invalid user input");
			return showMenu(menuData);
		}
	}

	public void runMenu() {
		BookStoreInterface bookStore = BookStoreFactory.getBookStoreManager();
		int selectedMenu = -1;
		while (selectedMenu != 5) {
			selectedMenu = showMenu(mainMenuData);
			switch (selectedMenu) {
			case 1:
				selectBookFromMenu(bookStore, "==== View Books ====",
						"To view details enter the book ID, to return press <Enter>.", bookStore.getAll());
				scan.nextLine();
				break;
			case 2:
				Book book = getBookFromConsole(bookStore);
				book = bookStore.addBook(book);
				System.out.println("Book [" + book.getId() + "] Saved");
				break;
			case 3:
				int selectedBook = selectBookFromMenu(bookStore, "==== Edit a Book ====",
						"Enter the book ID of the book you want to edit; to return press <Enter>.", bookStore.getAll());
				if (selectedBook != 0) {
					book = getBookFromConsole(bookStore);
					book.setId(selectedBook);
					bookStore.editBook(book);
				}
				scan.nextLine();
				break;
			case 4:
				searchForBook(bookStore);
				break;
			case 5:
				bookStore.flushChanges();
				System.out.println("Library saved.");
				break;
			default:
				break;
			}
		}

	}

	private void searchForBook(BookStoreInterface bookStore) {
		System.out.println("==== Search ====");
		System.out.println("Type in one or more keywords to search for");
		System.out.println("Search:");
		String searchKeyword = scan.nextLine();
		System.out.println("The following books matched your query.");
		List<Book> searchResult = bookStore.findByTitle(searchKeyword);
		selectBookFromMenu(bookStore, "==== Search ====",
				"The following books matched your query. Enter the book ID to see more details, or <Enter> to return.",
				searchResult);
		System.out.println("Press Any Key To Return");
		scan.nextLine();
	}

	private Book getBookFromConsole(BookStoreInterface bookStore) {
		System.out.println("Please enter the following information:");
		String title = null;
		do {
			System.out.println("Title:");
			title = scan.nextLine();
			if (title.equals("")) {
				System.out.println("Title can't be empty");
			}
		} while (title == "");

		System.out.println("Author:");
		String author = scan.nextLine();
		System.out.println("Description:");
		String desc = scan.nextLine();
		return new Book.BookBuilder().title(title).author(author).description(desc).build();
	}

	private int selectBookFromMenu(BookStoreInterface bookStore, String menuHeader, String menuMessage, List<Book> booksList) {
		int selectedBook = showMenu(new MenuData(menuHeader, menuMessage, getBookMenuOption(booksList)));
		if (selectedBook != 0) {
			System.out.println(BookMapper.getBookForMenuView(bookStore.findOne(selectedBook)));
		}
		return selectedBook;
	}

	private Map<Integer, String> getBookMenuOption(List<Book> booksList) {
		Map<Integer, String> optionMap = new HashMap<>();
		booksList.stream().forEach(book -> optionMap.put(book.getId(), "[" + book.getId() + "]" + book.getTitle()));
		return optionMap;
	}

	public static void main(String[] args) {
		new UserInterfaceMenuManager().runMenu();
	}
}
