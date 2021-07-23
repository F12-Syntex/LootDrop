
package com.lootdrop.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.lootdrop.config.Config;
import com.lootdrop.main.Lootdrop;
import com.lootdrop.utils.MessageUtils;

public class SetSpawn extends SubCommand {

    @SuppressWarnings("deprecation")
	@Override
    public void onCommand(Player player, String[] args) {
    	
    	String block = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getString("Settings.TargetBlock.Block");
    	
    	Material TargetMaterial;
    	
    	try {
    		TargetMaterial = Material.valueOf(block);
    	}catch (Exception e) {
    		MessageUtils.inform(player, " &c" + block + " is an invalid material!");
    		return;
    	}
    	
    	
    	Config config = Lootdrop.getInstance().configManager.getConfig("spawn");
    	
    	config.configuration.set("Spawn.World", player.getWorld().getName());
    	config.configuration.set("Spawn.X", player.getLocation().getBlockX());
    	config.configuration.set("Spawn.Y", player.getLocation().getBlockY());
    	config.configuration.set("Spawn.Z", player.getLocation().getBlockZ());
    	
    	MessageUtils.inform(player, " &6Coordinates have been analysed...");
    	MessageUtils.inform(player, " &6Mapping the region &c(&4THIS MAY CAUSE LAGG!&c)");
    	
    	Location loc = player.getLocation();
    	int r = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getInt("Settings.Raduis");
    	int h = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getInt("Settings.Height");
    
    	int data = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getInt("Settings.TargetBlock.Data");
    	
    	boolean hollow = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getBoolean("Settings.Hollow");
    	boolean sphere = Lootdrop.getInstance().configManager.getConfig("settings").getConfig().getBoolean("Settings.Sphere");
    	
    	List<String> coordinates = new ArrayList<String>();
    	
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y, z);
                        if(l.getBlock().getType() == TargetMaterial) {
                        	if(l.getBlock().getData() == (byte)data) {
                        	coordinates.add(x+","+y+","+z);
                        	}
                        }
                        }
                    }
        
        
        config.configuration.set("Spawn.Mapping", coordinates);
    	
        
    	config.save();
    	Lootdrop.getInstance().reload();
    	
    	if(coordinates.size() > 0) {
    		MessageUtils.inform(player, " &aTask completed! Found &6(&c" + coordinates.size() + "&6)&a spawn points!");
    	}else {
    		MessageUtils.inform(player, " &c Found 0 spawn points. please check your config and location...");
    	}
    	
    }

    @Override
    public String name() {
        return "setspawn";
    }

    @Override
    public String info() {
        return "sets the spawn";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.SETSPAWN;	
	}

}