package com.gmail.cactus.cata;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.commands.staff.StaffList;

public class GameObj {

	private HashMap<Player, String[]> mapInformation = new HashMap<Player, String[]>();
	private ArrayList<Player> gamemodeb = new ArrayList<Player>();
	private Main main;

	public GameObj(Main main) {

		this.main = main;

	}

	public HashMap<Player, String[]> getMapInformation() {

		return mapInformation;

	}

	public ArrayList<Player> getGamemodeB() {

		return gamemodeb;

	}

	public void reloadFixing() {

		for (Player player : Bukkit.getOnlinePlayers()) {

			FileConfiguration config = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "players/" + player.getUniqueId().toString() + ".yml"));
			StaffList staffList = StaffList.getStaff(config.getString("general.staff"));
			String[] info = { staffList.getPrefix(), staffList.getSuffix(), staffList.getNameOfStaff() };

			mapInformation.put(player, info);

		}

	}

}
