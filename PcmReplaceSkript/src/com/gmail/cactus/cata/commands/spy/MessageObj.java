package com.gmail.cactus.cata.commands.spy;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public class MessageObj {

	private HashMap<Player, Player> rep = new HashMap<Player, Player>();
	private ArrayList<String> social = new ArrayList<>();

	public HashMap<Player, Player> getMsgPlayer() {

		return rep;

	}
	
	public ArrayList<String> getSocial(){
		
		return social;
		
	}

}
