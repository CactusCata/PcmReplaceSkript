package com.gmail.cactus.cata.commands.other;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class ConfigOption implements CommandExecutor {

	private Main main;

	public ConfigOption(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("config")) {

			if (args.length < 1) {
				sender.sendMessage(PrefixMessage.PREFIX_CONFIG + "Veillez préciser une section !");
				return true;
			}

			if (args.length < 2) {
				sender.sendMessage(PrefixMessage.PREFIX_CONFIG + "Veillez préciser une section !");
				return true;
			}

			switch (args[0]) {

			case "help":

				configHelp(sender, args[1], main.getConfig());
				return true;

			case "change":

				if (args[1].equals("Vanish.connect")) {

					if (!(sender instanceof Player)) {
						sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
						return true;
					}

					Player player = (Player) sender;
					File file = new File(main.getDataFolder(), "players/" + player.getUniqueId() + ".yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					boolean b = !config.getBoolean(args[1]);
					config.set(args[1], b);
					player.sendMessage(
							PrefixMessage.PREFIX_CONFIG + "La valeur " + b + " a été mise à " + args[1] + " !");
					try {
						config.save(file);
					} catch (IOException e) {

						e.printStackTrace();
					}

					return true;

				}

				boolean b = false;

				for (String key : main.getConfig().getConfigurationSection("Vanish").getKeys(false)) {
					if (key.equals(args[1]))
						b = true;

				}

				if (!b) {
					sender.sendMessage(PrefixMessage.PREFIX_CONFIG + args[1] + " n'est pas une section correcte !");
					return true;
				}

				boolean c = !main.getConfig().getBoolean("Vanish." + args[1]);
				main.getConfig().set("Vanish." + args[1], c);
				main.saveConfig();
				sender.sendMessage(
						PrefixMessage.PREFIX_CONFIG + "La valeur de Vanish." + args[1] + " a été mise à " + c + " !");
				return true;

			default:

				sender.sendMessage(PrefixMessage.PREFIX_CONFIG + args[0] + " n'est pas une option correcte !");
				return true;

			}

		}
		return false;

	}

	private void configHelp(CommandSender sender, String string, FileConfiguration config) {

		if (!checkExist(string, config)) {
			sender.sendMessage(PrefixMessage.PREFIX_CONFIG + "La section " + string + " n'est pas valable !");
			return;
		}

		String message = PrefixMessage.PREFIX_CONFIG + "Liste des options " + string + " :";
		for (String key : main.getConfig().getConfigurationSection(string).getKeys(false))
			message += "\n" + key + " : " + config.getString(string + "." + key);
		sender.sendMessage(message);
	}

	private boolean checkExist(String string, FileConfiguration config) {

		for (String key : main.getConfig().getKeys(false))
			if (key.equalsIgnoreCase(string))
				return true;
		return false;

	}

}
