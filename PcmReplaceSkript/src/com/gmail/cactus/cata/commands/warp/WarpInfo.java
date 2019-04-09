package com.gmail.cactus.cata.commands.warp;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class WarpInfo implements CommandExecutor {

	private Main main;
	private CommandManager cmdManag;

	public WarpInfo(Main main, CommandManager cmdManag) {
		this.main = main;
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("warpinfo")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}
			
			if (args.length < 0) {

				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le warp !");
				return true;

			}

			File file = new File(main.getDataFolder(), "warps.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);

			try {
				String x = config.getString("warps." + args[0] + ".x");
				String y = config.getString("warps." + args[0] + ".y").toString();
				String z = config.getString("warps." + args[0] + ".z").toString();
				String pitch = config.getString("warps." + args[0] + ".pitch").toString();
				String yaw = config.getString("warps." + args[0] + ".yaw").toString();
				String monde = config.getString("warps." + args[0] + ".monde").toString();

				sender.sendMessage(PrefixMessage.PREFIX + "Le warp " + args[0] + " a comme info :\n" + "x: " + x + "\ny: "
						+ y + "\nz: " + z + "\npitch: " + pitch + "\nyaw: " + yaw + "\nmonde: " + monde);
			} catch (NullPointerException e) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le warp " + args[0] + " n'existe pas !");
			}

			return true;

		}

		return false;
	}

}
