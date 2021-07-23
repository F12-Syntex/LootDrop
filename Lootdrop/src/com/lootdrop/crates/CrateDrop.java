package com.lootdrop.crates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.lootdrop.main.Lootdrop;

public class CrateDrop {

	public List<Location> crates = new ArrayList<Location>();
	private BukkitScheduler scheduler;	
	
	@SuppressWarnings("deprecation")
	public void dropCrates() {
		
		crates = new ArrayList<Location>();
		
		if(this.scheduler != null) {
			this.scheduler.cancelAllTasks();
		}
		
		this.scheduler = Lootdrop.getInstance().getServer().getScheduler();
		
		FileConfiguration config = Lootdrop.getInstance().configManager.getConfig("spawn").getConfig();
		
    	int drops = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getInt("Settings.Drops");
    	long delay = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getLong("Settings.Delay");
		
		
		List<String> coords = config.getStringList("Spawn.Mapping");
		
		Collections.shuffle(coords);
		
		if(coords.size() > drops) {
			coords = coords.subList(0, drops);
		}
		
		for(String i : coords) {
			
			String[] loc = i.split(",");
			
			World world = Bukkit.getWorld(config.getString("Spawn.World"));
			
			double x = Integer.parseInt(loc[0]);
			double z = Integer.parseInt(loc[2]);
			
			scheduler.scheduleSyncRepeatingTask(Lootdrop.getInstance(), new BukkitRunnable() {
	        
				double prevY = 100;
				double y = 100;
				Block spawn = world.getBlockAt(new Location(world, x, y, z));
				boolean running = true;
				
				
				@Override
	            public void run() {
					
					if(!running) return;
					
					spawn = world.getBlockAt(new Location(world, x, y, z));
					
    				if(spawn.isEmpty()) {
    					world.getBlockAt(new Location(world, x, prevY, z)).setType(Material.AIR);
    					spawn.setType(Material.CHEST);
	            	}else {
	            		Block latest = world.getBlockAt(new Location(world, x, y, z));
	            		crates.add(latest.getLocation());
	            		System.out.println(x + " " + y + " " + z);
							
	            		running = false;
	            	}
	            	
    				prevY = y;
    				y--;
    				
	            }  	
	        }, 0L, delay);
			
		}
		
		
	}
	
}
