package com.gmail.cactus.cata.commands.staff;

public enum StaffList {

	FONDATEUR("§c§l[Fondateur]", "§b", "Fondateur"),
	ADMINISTRATEUR("§c[Admin]", "§b", "Administrateur"),
	DEVELOPPEUR("§2[Dev]","§b","Developpeur"),
	RESPONSABLE_EVENT("§5[Resp.Event]", "§3", "Resp.Event"),
	RESPONSABLE("§5[Responsable]", "§3", "Responsable"),
	ANIMATEUR("§9[Animateur]", "§b", "Animateur"),
	ANIMATRICE("§9[Animatrice]", "§b", "Animatrice"),
	ORGANISATEUR("§9[Organisateur]", "§d", "Organisateur"),
	ORGANISATRICE("§9[Organisatrice]", "§d", "Organisatrice"),
	COMMUNITYMANAGER("§1[Commu.M]","§d","CommunityM"),
	MODERATEUR("§d[Modo]", "§3", "Moderateur"),
	GUARDIAN("§a[Guardian]", "§3", "Guardian"),
	GUIDE("§7[Guide]", "§3", "Guide"),
	ANCIEN("§8[Ancien]", "§3", "Ancien"),
	PCT("§2[PCT]", "§3", "Pct"),
	AMI("§6[Ami]", "§3", "Ami"),
	AUCUN("§e", "§f", "Aucun"),
	NULL(null, null, "null");

	private String prefix;
	private String suffix;
	private String nameOfStaff;

	private StaffList(String prefix, String suffix, String nameOfStaff) {

		this.prefix = prefix;
		this.suffix = suffix;
		this.nameOfStaff = nameOfStaff;

	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public String getNameOfStaff() {
		return this.nameOfStaff;
	}

	public boolean contains(String str) {
		for (StaffList staff : StaffList.values())
			if (staff.getNameOfStaff().equals(str))
				return true;
		return false;
	}

	public static StaffList getStaff(String str) {
		for (StaffList staff : StaffList.values())
			if (staff.getNameOfStaff().equals(str))
				return staff;
		return StaffList.NULL;
	}

}
