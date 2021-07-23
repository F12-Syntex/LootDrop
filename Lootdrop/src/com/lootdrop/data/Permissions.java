package com.lootdrop.data;

import org.bukkit.configuration.file.FileConfiguration;

import com.lootdrop.main.Lootdrop;

public class Permissions {

	public static Permissions instance;
	
	public FileConfiguration permissions = Lootdrop.instance.configManager.getConfig("permissions").configuration;
	
	public final String DEFAULT = permissions.getString("Permissions.default");
	public final String ADMIN = permissions.getString("Permissions.admin");
	public final String BYPASS = permissions.getString("Permissions.timer_bypass");
	public final String SETSPAWN = permissions.getString("Permissions.setspawn");
	public final String TIME = permissions.getString("Permissions.time");
	
	
	public Permissions() {
		
	}
	
	public static Permissions instance() {
		return instance;
	}
	
	public static void reInitialize() {
		instance = new Permissions();
	}
	
	
}
