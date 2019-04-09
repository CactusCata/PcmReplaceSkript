package com.gmail.cactus.cata.enums;

public enum PrefixMessage {

	PREFIX("§1[§bMondeEvent§1]§e "),

	PREFIX_SENDER_BE_PLAYER(PrefixMessage.PREFIX + "§4La commande ne peut etre execute que par un joueur !"),

	PREFIX_SPY("§1[§3Spy§1]§7["),
	
	PREFIX_CONFIG("§5[§7Config§5]§e "),
	
	PREFIX_CANT_SEND_COMMAND("§4[§6Erreur§4] Commande désactivé !"),

	PREFIX_VANISH("§f[§bVanish§f]§e ");

	private String text;

	private PrefixMessage(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}