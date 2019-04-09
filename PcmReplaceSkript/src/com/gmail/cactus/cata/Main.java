package com.gmail.cactus.cata;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.cactus.cata.commands.CommandManager;
import com.gmail.cactus.cata.commands.other.Broadcast;
import com.gmail.cactus.cata.commands.other.Commands;
import com.gmail.cactus.cata.commands.other.ConfigOption;
import com.gmail.cactus.cata.commands.other.Fly;
import com.gmail.cactus.cata.commands.other.Gamemodeb;
import com.gmail.cactus.cata.commands.other.Heal;
import com.gmail.cactus.cata.commands.other.InfoPlayer;
import com.gmail.cactus.cata.commands.other.Killb;
import com.gmail.cactus.cata.commands.other.Spawn;
import com.gmail.cactus.cata.commands.other.Speed;
import com.gmail.cactus.cata.commands.other.Tps;
import com.gmail.cactus.cata.commands.spy.MessageObj;
import com.gmail.cactus.cata.commands.spy.Msg;
import com.gmail.cactus.cata.commands.spy.R;
import com.gmail.cactus.cata.commands.spy.SocialSpy;
import com.gmail.cactus.cata.commands.spy.SpyList;
import com.gmail.cactus.cata.commands.staff.SetStaff;
import com.gmail.cactus.cata.commands.tp.Tpb;
import com.gmail.cactus.cata.commands.tp.Tppos;
import com.gmail.cactus.cata.commands.warp.DelWarp;
import com.gmail.cactus.cata.commands.warp.SetWarp;
import com.gmail.cactus.cata.commands.warp.Warp;
import com.gmail.cactus.cata.commands.warp.WarpInfo;
import com.gmail.cactus.cata.commands.warp.Warps;
import com.gmail.cactus.cata.other.Sign;
import com.gmail.cactus.cata.other.Weather;
import com.gmail.cactus.cata.vanish.EventsListener;
import com.gmail.cactus.cata.vanish.Vanish;
import com.gmail.cactus.cata.vanish.VanishManager;

public class Main extends JavaPlugin {

	public void onEnable() {

		PluginManager pm = getServer().getPluginManager();
		getConfig().options().copyDefaults(true);
		saveConfig();

		CommandManager cmdManag = new CommandManager(this);
		GameObj gameObj = new GameObj(this);
		MessageObj messageObj = new MessageObj();
		VanishManager vanishManager = new VanishManager();

		cmdManag.initializeCommand();

		pm.registerEvents(new Sign(), (this));
		pm.registerEvents(new Weather(), (this));
		pm.registerEvents(new PlayerSendMessage(this, gameObj, vanishManager), (this));
		pm.registerEvents(new LeaveGame(this, gameObj, messageObj, vanishManager), (this));
		pm.registerEvents(new JoinGame(this, gameObj, vanishManager), (this));
		pm.registerEvents(new EventsListener(this, vanishManager, gameObj), (this));

		getCommand("broadcast").setExecutor(new Broadcast(cmdManag));
		getCommand("spawn").setExecutor(new Spawn(cmdManag));
		getCommand("gamemodeb").setExecutor(new Gamemodeb(cmdManag, gameObj));
		getCommand("setstaff").setExecutor(new SetStaff(this, gameObj, vanishManager));
		getCommand("setwarp").setExecutor(new SetWarp(this, cmdManag));
		getCommand("delwarp").setExecutor(new DelWarp(this, cmdManag));
		getCommand("warp").setExecutor(new Warp(this, cmdManag));
		getCommand("warps").setExecutor(new Warps(this, cmdManag));
		getCommand("warpinfo").setExecutor(new WarpInfo(this, cmdManag));
		getCommand("socialspy").setExecutor(new SocialSpy(cmdManag, messageObj));
		getCommand("spylist").setExecutor(new SpyList(cmdManag, messageObj));
		getCommand("msg").setExecutor(new Msg(cmdManag, messageObj, vanishManager));
		getCommand("r").setExecutor(new R(cmdManag, messageObj));
		getCommand("speed").setExecutor(new Speed(cmdManag));
		getCommand("killb").setExecutor(new Killb(cmdManag));
		getCommand("tppos").setExecutor(new Tppos(cmdManag));
		getCommand("tpb").setExecutor(new Tpb(cmdManag, vanishManager));
		getCommand("fly").setExecutor(new Fly(cmdManag));
		getCommand("infoplayer").setExecutor(new InfoPlayer(this, cmdManag));
		getCommand("vanish").setExecutor(new Vanish(this, vanishManager, cmdManag));
		getCommand("config").setExecutor(new ConfigOption(this));
		getCommand("lag").setExecutor(new Tps(this, cmdManag));
		getCommand("commands").setExecutor(new Commands(cmdManag));
		getCommand("heal").setExecutor(new Heal(cmdManag));

	}

}
