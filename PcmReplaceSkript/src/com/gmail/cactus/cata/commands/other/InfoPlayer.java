package com.gmail.cactus.cata.commands.other;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.api.UUIDFetcher;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class InfoPlayer implements CommandExecutor {

	private Main main;
	private CommandManager cmdManag;

	public InfoPlayer(Main main, CommandManager cmdManag) {
		this.main = main;
		this.cmdManag = cmdManag;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("infoplayer")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (args.length < 1) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez le joueur !");
				return true;
			}

			if (args.length < 2) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser l'information !");
				return true;
			}

			File file;
			FileConfiguration config;
			Player target = Bukkit.getPlayerExact(args[0]);
			if (target != null && target.isOnline()) {

				try {
					file = new File(main.getDataFolder(), "players/" + target.getUniqueId().toString() + ".yml");
				} catch (NullPointerException e) {
					sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'a pas été trouvé !");
					return true;
				}

			} else {

				UUID uuid = UUIDFetcher.getUUIDOf(args[0]);

				try {
					file = new File(main.getDataFolder(), "players/" + uuid.toString() + ".yml");
				} catch (NullPointerException e) {
					sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'a pas été trouvé !");
					return true;
				}

			}

			config = YamlConfiguration.loadConfiguration(file);

			if (config.get(args[1]) == null) {
				sender.sendMessage(PrefixMessage.PREFIX + "La statistique " + args[1] + " n'a pas été trouvé !");
				return true;
			}

			sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " --> " + args[1] + " a pour valeur : "
					+ config.get(args[1]));

			return true;

		}
		return false;
	}

}
