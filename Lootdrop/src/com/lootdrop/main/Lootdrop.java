package com.lootdrop.main;
import java.io.File;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.lootdrop.config.ConfigManager;
import com.lootdrop.cooldown.CooldownManager;
import com.lootdrop.cooldown.CooldownTick;
import com.lootdrop.cooldown.Cooldowns;
import com.lootdrop.cooldown.SingleUseCooldownEntity;
import com.lootdrop.crates.CrateDrop;
import com.lootdrop.data.GenericMessages;
import com.lootdrop.data.Permissions;
import com.lootdrop.events.EventHandler;


public class Lootdrop extends JavaPlugin implements Listener{


    public static Lootdrop instance;
    public com.lootdrop.commands.CommandManager CommandManager;
    public ConfigManager configManager;
    public EventHandler eventHandler;
    public CooldownManager cooldownManager;
    public CooldownTick cooldownTick;
	public File ParentFolder;
	public SingleUseCooldownEntity mainTimer;
	public CrateDrop drops;
	
	@Override
	public void onEnable(){
		
		ParentFolder = getDataFolder();
	    instance = this;
		
	    configManager = new ConfigManager();
	    configManager.setup(this);
	    
	    this.reload();
	    
	    eventHandler = new EventHandler();
	    eventHandler.setup();
	    
	    this.cooldownManager = new CooldownManager();

	    this.cooldownTick = new CooldownTick();
	    this.cooldownTick.schedule();
	    
	    this.drops = new CrateDrop();
	    
	    this.CommandManager = new com.lootdrop.commands.CommandManager();
	    this.CommandManager.setup(this);
	    
	    int seconds = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getInt("Settings.TimeForDrop");
	    
	    
	    this.mainTimer = new SingleUseCooldownEntity(seconds, true) {
			
			@Override
			public void tick() {
				
			}
			
			@Override
			public void onTaskEnd() {
				drops.dropCrates();
			}
		};
	    
	    this.cooldownManager.registerSinglyCooldown(mainTimer);
	 
	}
	
	
	@Override
	public void onDisable(){
		this.configManager.dispose();
		this.eventHandler = null;
		HandlerList.getRegisteredListeners(instance);
	}

	public void reload() {
	    GenericMessages.reInitialize();
	    Permissions.reInitialize();
	    Cooldowns.reInitialize();
	}
		

	public static Lootdrop getInstance() {
		return instance;
	}
		
	
}
