package com.gmail.cactus.cata.vanish;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.enums.PrefixMessage;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Vanish implements CommandExecutor {

	private Main main;
	private VanishManager vanishManager;
	private CommandManager cmdManag;

	public Vanish(Main main, VanishManager vanishManager, CommandManager cmdManag) {
		this.main = main;
		this.vanishManager = vanishManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("vanish")) {

			if (!cmdManag.isValid(cmd.getName())) {
				sender.sendMessage(PrefixMessage.PREFIX_CANT_SEND_COMMAND + "");
				return true;
			}

			if (!(sender instanceof Player)) {

				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player playersender = (Player) sender;
			File file;
			FileConfiguration config = null;

			if (vanishManager.getList().contains(playersender)) {

				for (Player playergetter : Bukkit.getOnlinePlayers())
					playergetter.showPlayer(playersender);

				file = new File(main.getDataFolder(), "players/" + playersender.getUniqueId() + ".yml");
				config = YamlConfiguration.loadConfiguration(file);

				playersender.sendMessage(PrefixMessage.PREFIX_VANISH + "Vous n'êtes plus en vanish");
				playersender.setDisplayName(
						config.get("general.prefix") + playersender.getName() + config.get("general.suffix"));
				playersender.setPlayerListName(playersender.getDisplayName());
				if (playersender.getGameMode() == GameMode.SURVIVAL || playersender.getGameMode() == GameMode.ADVENTURE)
					playersender.setAllowFlight(false);
				playersender.removePotionEffect(PotionEffectType.NIGHT_VISION);

				if (config.get("general.prefix").equals("§9[Organisateur]"))
					playersender.setGameMode(GameMode.ADVENTURE);
				vanishManager.getList().remove(playersender);

				return true;
				// <-- Avant ça c'est quand le joueur est en Vanish et qu'on
				// l'enleve du Vanish
			} else {

				for (Player playergetter : Bukkit.getOnlinePlayers()) {

					file = new File(main.getDataFolder(), "players/" + playergetter.getUniqueId() + ".yml");
					config = YamlConfiguration.loadConfiguration(file);

					if (config.get("general.staff").equals("Aucun"))
						playergetter.hidePlayer(playersender);

				}

				IChatBaseComponent comp = ChatSerializer.a(
						"[\"\",{\"text\":\"[\"},{\"text\":\"Vanish\",\"color\":\"aqua\"},{\"text\":\"/\"},{\"text\":\"Config\",\"color\":\"gray\"},{\"text\":\"] \"},{\"text\":\"En clickant\",\"color\":\"yellow\"},{\"text\":\" \"},{\"text\":\"[\",\"bold\":\"true\",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/config change Vanish.connect true\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"Execute la commande:\",\"color\":\"yellow\"},{\"text\":\" \"},{\"text\":\"/config change Vanish.connect true\",\"italic\":\"true\",\"color\":\"gold\"}]}},{\"text\":\"ICI\",\"bold\":\"true\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/config change Vanish.connect true\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"Execute la commande:\",\"color\":\"yellow\"},{\"text\":\" \"},{\"text\":\"/config change Vanish.connect true\",\"italic\":\"true\",\"color\":\"gold\"}]}},{\"text\":\"]\",\"bold\":\"true\",\"color\":\"dark_gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/config change Vanish.connect true\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"Execute la commande:\",\"color\":\"yellow\"},{\"text\":\" \"},{\"text\":\"/config change Vanish.connect true\",\"italic\":true,\"color\":\"gold\"}]}},{\"text\":\" \"},{\"text\":\"vous serez mis en Vanish dès que vous vous connecterez !\",\"color\":\"yellow\"}]");
				PacketPlayOutChat packet = new PacketPlayOutChat(comp);
				((CraftPlayer) playersender).getHandle().playerConnection.sendPacket(packet);

				if (config.get("general.prefix").equals("§9[Organisateur]"))
					playersender.setGameMode(GameMode.SURVIVAL);

				playersender.sendMessage(PrefixMessage.PREFIX_VANISH + "Vous êtes maintenant en vanish");
				playersender.setDisplayName(playersender.getDisplayName() + "§f[Vanish]");
				playersender.setPlayerListName(playersender.getDisplayName());

				playersender.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, true));
				playersender.setAllowFlight(true);
				vanishManager.getList().add(playersender);

				return true;

			}

		}

		return false;
	}

}
