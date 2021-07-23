package com.lootdrop.data;

import org.bukkit.configuration.file.FileConfiguration;

import com.lootdrop.main.Lootdrop;

public class Spawn {

	private static Spawn instance;
	
	public FileConfiguration config = Lootdrop.getInstance().configManager.getConfig("spawn").configuration;

	public final String space = "\n" + "\n";
	
	public final String SPAWN = config.getString("Spawn.World");
	public final int X = config.getInt("Spawn.X");
	public final int Y = config.getInt("Spawn.Y");
	public final int Z = config.getInt("Spawn.Z");
	
	public Spawn() {
		
	}

	public static Spawn instance() {
		return instance;
	}
	
	public static void reInitialize() {
		instance = new Spawn();
	}
	
	
}
