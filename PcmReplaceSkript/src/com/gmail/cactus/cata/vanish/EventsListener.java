package com.gmail.cactus.cata.vanish;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.util.Vector;

import com.gmail.cactus.cata.GameObj;
import com.gmail.cactus.cata.Main;
import com.gmail.cactus.cata.enums.PrefixMessage;

public class EventsListener implements Listener {

	private Main main;
	private VanishManager vanishManag;
	private GameObj gameObj;

	public EventsListener(Main main, VanishManager vanishManager, GameObj gameObj) {
		this.main = main;
		this.vanishManag = vanishManager;
		this.gameObj = gameObj;
	}

	@EventHandler
	public void canDropItems(PlayerDropItemEvent event) {

		if (config(event.getPlayer(), "Vanish.CanDropItems", "Vous ne pouvez rien drop en vanish !"))
			event.setCancelled(true);
	}

	@EventHandler
	public void onTakeDamage(EntityDamageByEntityEvent event) {

		// Player senddmgplayer = (Player) event.getDamager();
		// Player getdmgplayer = (Player) event.getEntity();
		
		if (gameObj.getGamemodeB().contains(event.getEntity()))
	    {
	      Player sendDamager = (Player)event.getDamager();
	      sendDamager.sendMessage(PrefixMessage.PREFIX + "Vous ne pouvez pas taper les Organisateurs !");
	      event.setCancelled(true);
	    }

		if (config((Player) event.getDamager(), "Vanish.CanHitPlayersWithVanish",
				"Vous ne pouvez pas frapper dans la bouche les gens tout en étant en vanish ! (c'est faible !)"))
			event.setCancelled(true);

	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {

		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if (vanishManag.getList().contains(p))
			e.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent e) {

		if (((e.getEntity() instanceof Player))) {
			Player p = (Player) e.getEntity();
			if ((vanishManag.getList().contains(p)) && (e.getFoodLevel() <= p.getFoodLevel()))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPickupPlayer(PlayerPickupItemEvent event) {

		if (vanishManag.getList().contains(event.getPlayer())) {
			if (config(event.getPlayer(), "Vanish.CanPickupItems",
					"Vous ne pouvez pas récupérer d'items en vanish !")) {
				event.getItem().setPickupDelay(20);
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onPlayerArrowBlock(EntityDamageByEntityEvent event) {
		Entity entityDamager = event.getDamager();
		Entity entityDamaged = event.getEntity();
		if ((entityDamager instanceof Arrow)) {
			Arrow arrow = (Arrow) entityDamager;
			if (((entityDamaged instanceof Player)) && ((arrow.getShooter() instanceof Player))) {
				Player damaged = (Player) entityDamaged;
				if (vanishManag.getList().contains(damaged)) {
					Vector velocity = arrow.getVelocity();
					damaged.teleport(damaged.getLocation().add(0.0D, 2.0D, 0.0D));
					Arrow nextArrow = (Arrow) arrow.getShooter().launchProjectile(Arrow.class);
					nextArrow.setVelocity(velocity);
					nextArrow.setBounce(false);
					nextArrow.setShooter(arrow.getShooter());
					nextArrow.setFireTicks(arrow.getFireTicks());
					nextArrow.setCritical(arrow.isCritical());
					nextArrow.setKnockbackStrength(arrow.getKnockbackStrength());
					event.setCancelled(true);
					arrow.remove();
				}
			}
		}

	}

	@EventHandler
	public void onBlockCanBuild(BlockCanBuildEvent event) {
		Block block = event.getBlock();
		Location loc = block.getLocation();
		for (Player player : block.getWorld().getPlayers()) {
			if (!vanishManag.getList().contains(player))
				continue;
			if (player.getLocation().distanceSquared(loc) <= 2.0D)
				event.setBuildable(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (config(event.getPlayer(), "Vanish.CanBreakBlock", "Vous ne pouvez pas casser de block en Vanish !"))
			event.setCancelled(true);
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent event) {

		if (config(event.getPlayer(), "Vanish.CanPlaceBlock", "Vous ne pouvez pas placer de block en Vanish !"))
			event.setCancelled(true);

	}

	@EventHandler
	public void getExperience(PlayerExpChangeEvent event) {

		if (config(event.getPlayer(), "Vanish.AllowXP", "Vous ne pouvez pas récuperer l'xp au sol !"))
			event.setAmount(0);
	}

	private boolean config(Player player, String adresse, String message) {

		if (vanishManag.getList().contains(player)) {
			try {
				if (main.getConfig().getBoolean(adresse) == false) {
					player.sendMessage(PrefixMessage.PREFIX_VANISH + message);
					return true;
				}
			} catch (NullPointerException e) {
				main.getConfig().set(adresse, false);
				main.saveConfig();
			}

		}
		return false;

	}

}