package com.gi.task.interfaces;

import java.util.HashMap;

import com.gi.task.models.Book;
//Interface for Storage management to provide many shapes of storage implementation
public interface DataStorageStrategy {
	HashMap<Integer,Book> loadAll();
	void flush(HashMap<Integer,Book>booksToBeSaved);
	
}
