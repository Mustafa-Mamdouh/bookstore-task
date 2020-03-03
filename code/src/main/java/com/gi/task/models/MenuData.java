package com.gi.task.models;

import java.util.HashMap;
import java.util.Map;
//Date container for console menu
public class MenuData {
	String header;
	Map<Integer,String> menuItems;
	String menuMessage;
	public MenuData(String header,String menuMessage, Map<Integer,String> menuItems) {
		super();
		this.header = header;
		this.menuItems = menuItems;
		this.menuMessage=menuMessage;
	}
//	public MenuData(String header,String menuMessage, String... menuItems) {
//		super();
//		this.header = header;
//		this.menuMessage=menuMessage;
//		this.menuItems = Arrays.asList(menuItems);
//	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Map<Integer,String> getMenuItems() {
		if (menuItems == null) {
			menuItems = new HashMap<>();
		}
		return menuItems;
	}

	public void setMenuItems(Map<Integer,String> menuItems) {
		this.menuItems = menuItems;
	}

	public String getMenuMessage() {
		return menuMessage;
	}
	public void setMenuMessage(String menuMessage) {
		this.menuMessage = menuMessage;
	}
	public void addMenuOption(Integer optionKey,String option) {
		getMenuItems().put(optionKey,option);
	}
}
