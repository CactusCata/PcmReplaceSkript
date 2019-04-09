package com.gmail.cactus.cata.commands;

import java.util.HashMap;

import com.gmail.cactus.cata.Main;

public class CommandManager {

	private Main main;
	private HashMap<String, Boolean> mapCommand = new HashMap<String, Boolean>();

	public CommandManager(Main main) {
		this.main = main;
	}

	public boolean isValid(String cmd) {

		return mapCommand.get(cmd);

	}

	public HashMap<String, Boolean> getMap() {

		return mapCommand;

	}

	public void initializeCommand() {

		for (String command : main.getDescription().getCommands().keySet()) {
			mapCommand.put(command, true);
		}
	}

}
