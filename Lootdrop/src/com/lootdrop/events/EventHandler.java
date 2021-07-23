package com.lootdrop.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.lootdrop.main.Lootdrop;

public class EventHandler {

    public static List<SubEvent> events = new ArrayList<SubEvent>();
	
    private Plugin plugin = Lootdrop.instance;
    
	public void setup() {
		events.add(new OpenCrate());
		
		
		events.forEach(i -> plugin.getServer().getPluginManager().registerEvents(i, plugin));
	}
	
}
