package com.lootdrop.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.lootdrop.identification.ConfigID;

public class Settings extends Config{

	@Override
	public int version() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return "settings config";
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "settings";
	}

	@Override
	public ConfigID indentification() {
		// TODO Auto-generated method stub
		return ConfigID.PERMISSIONS;
	}

	@Override
	public FileConfiguration getInstance() {
		// TODO Auto-generated method stub
		return configuration;
	}

	@Override
	public boolean exception() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String folder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveData() {
		// TODO Auto-generated method stub
		return false;
	}

}
