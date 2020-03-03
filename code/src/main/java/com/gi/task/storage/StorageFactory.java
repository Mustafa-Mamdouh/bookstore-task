package com.gi.task.storage;

import com.gi.task.exceptions.ApplicationException;
import com.gi.task.interfaces.DataStorageStrategy;
//DataStroage Factory
public class StorageFactory {
	public static DataStorageStrategy getDataStorage(String type) {
		DataStorageStrategy dataStorageStrategy = null;
		switch (type) {
		case "file":
			dataStorageStrategy = new FileDataStorage();
			break;
		case "database":
			throw new ApplicationException("Not Supported yet");
		default:
			throw new ApplicationException("Can not find implemntation for type " + type);
		}
		return dataStorageStrategy;
	}
}
