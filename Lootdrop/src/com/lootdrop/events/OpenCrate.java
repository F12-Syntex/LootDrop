package com.lootdrop.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import com.lootdrop.data.Crates;
import com.lootdrop.main.Lootdrop;

public class OpenCrate extends SubEvent{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Crate Handler";
	}

	@Override
	public String bypass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return "Allows users to open crates and get their items.";
	}

	
	@EventHandler
	public void open(PlayerInteractEvent e) {

		if(e.getClickedBlock() == null) return;
		if(e.getClickedBlock().getType() != Material.CHEST) return;
		
		Location block = e.getClickedBlock().getLocation();
		
		for(Location i : Lootdrop.getInstance().drops.crates) {
			
			if(i.getBlockX() == block.getBlockX()) {
				if(i.getBlockZ() == block.getBlockZ()) {
					if(i.getBlockZ() == block.getBlockZ()) {
						block.getBlock().setType(Material.AIR);
						Crates.getContents().forEach(o -> {
							e.getPlayer().getInventory().addItem(o);
						});
					}
				}
			}
			
		}

		
		
	}
	
}
