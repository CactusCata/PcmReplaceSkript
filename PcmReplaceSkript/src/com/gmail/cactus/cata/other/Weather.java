package com.gmail.cactus.cata.other;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Weather implements Listener {

	@EventHandler
	public void ChangeWeather(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

}
