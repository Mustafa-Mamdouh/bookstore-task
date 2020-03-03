package com.gi.task.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.gi.task.exceptions.ApplicationException;
import com.gi.task.interfaces.DataStorageStrategy;
import com.gi.task.mappers.BookMapper;
import com.gi.task.models.Book;

//This class responsible for writing and reading data from desk based storage
class FileDataStorage implements DataStorageStrategy {

	String dataFilePath = null;
	//Initialize file data storage class and create data file in the bas dir if not existed
	//May throw exception if java failed to create the file due to permission or any other reason
	public FileDataStorage() {
		String currentDirectory = System.getProperty("user.dir");
		dataFilePath = currentDirectory + "/datafilel";
		File f = new File(dataFilePath);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				throw new ApplicationException("Cann't create data file", e);
			}
		}
	}
	//Load all data from file
	public HashMap<Integer, Book> loadAll() {
		List<String> fileLines = null;
		try {
			fileLines = Files.readAllLines(Paths.get(dataFilePath));
		} catch (FileNotFoundException e) {
			// Suppress Exception When No Data file found
		} catch (Exception e) {
			throw new ApplicationException("Error On Loading Data to File " + dataFilePath, e);
		}
		return BookMapper.getBooksFromFile(fileLines);
	}

	//write all data from file
	//TODO Planned enhancement is to check for edited books only and update in file instead of rewriting it
	public void flush(HashMap<Integer, Book> booksToBeSaved) {
		List<String> collect = booksToBeSaved.values().stream().map(book -> BookMapper.getBookString(book))
				.collect(Collectors.toList());
		try {
			Files.write(Paths.get(dataFilePath), collect);
		} catch (IOException e) {
			throw new ApplicationException("Error On Writing Data to File " + dataFilePath, e);
		}
	}

}
