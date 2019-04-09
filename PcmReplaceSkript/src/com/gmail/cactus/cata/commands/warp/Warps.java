package com.gmail.cactus.cata.commands.warp;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Warps implements CommandExecutor {

	private Main main;
	private CommandManager cmdManag;

	public Warps(Main main, CommandManager cmdManag) {
		this.main = main;
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("warps")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			File file = new File(main.getDataFolder(), "warps.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);

			String warps = PrefixMessage.PREFIX + "Liste de tous les warps :";
			int i = 0;

			if (!file.exists()) {
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (config.getConfigurationSection("warps.") == null) {

				sender.sendMessage(PrefixMessage.PREFIX + "Il n'y a aucun warp existant !");
				return true;

			}

			for (String key : config.getConfigurationSection("warps.").getKeys(false)) {
				warps += (i < 1) ? "" : ',';
				warps += " " + key;
				i++;
			}

			warps += " !";

			sender.sendMessage(warps);
			return true;

		}

		return false;
	}

}
