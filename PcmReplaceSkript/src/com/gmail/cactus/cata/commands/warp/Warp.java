package com.gmail.cactus.cata.commands.warp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class Warp implements CommandExecutor {

	private Main main;
	private CommandManager cmdManag;

	public Warp(Main main, CommandManager cmdManag) {
		this.main = main;
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("warp") || cmd.getName().equalsIgnoreCase("wp")) {
			
			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			if (args.length == 0) {

				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le warp");
				return true;

			}

			if (!args[0].equalsIgnoreCase("event")) {
				if (!sender.hasPermission("pcm.warp")) {
					sender.sendMessage(ChatColor.RED
							+ "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
					return true;
				}
			}

			Player player = (Player) sender;
			String warp = args[0];

			File file = new File(main.getDataFolder(), "warps.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);

			if (config.get("warps." + warp + ".x") == null) {
				player.sendMessage(PrefixMessage.PREFIX + "Le warp " + warp + " n'existe pas !");
				return true;
			}

			double x = (double) config.get("warps." + warp + ".x");
			double y = (double) config.get("warps." + warp + ".y");
			double z = (double) config.get("warps." + warp + ".z");
			double pitch = (double) config.get("warps." + warp + ".pitch");
			double yaw = (double) config.get("warps." + warp + ".yaw");
			World world = Bukkit.getWorld((String) config.get("warps." + warp + ".monde"));

			player.teleport(new Location(world, x, y, z, (float) yaw, (float) pitch));
			player.sendMessage(PrefixMessage.PREFIX + "Vous avez bien été téléporté au warp " + warp + " !");

			return true;

		}

		return true;
	}

}
