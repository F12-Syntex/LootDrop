package com.lootdrop.cooldown;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitScheduler;

import com.lootdrop.main.Lootdrop;

public class CooldownTick {
	
	private BukkitScheduler scheduler;

	public CooldownTick() {
		this.scheduler = Lootdrop.getInstance().getServer().getScheduler();
	}

	public void schedule() {
		
        scheduler.scheduleSyncRepeatingTask(Lootdrop.getInstance(), new Runnable() {
            @Override
            public void run() {
            	final List<CooldownEntity> cooldowns = Lootdrop.getInstance().cooldownManager.getCooldowns();
            	final List<CooldownEntity> remove = new ArrayList<CooldownEntity>();
            	
            	for(CooldownEntity i : cooldowns) {
            		i.onTick();
            		
            		if(i instanceof SingleUseCooldownEntity) {
            			SingleUseCooldownEntity dispose = (SingleUseCooldownEntity)i;
            			if(!(dispose.running) && !dispose.forever) {
            				remove.add(dispose);
            			}
            		}
            	
            	}
            	
            	Lootdrop.getInstance().cooldownManager.getCooldowns().removeAll(remove);
            	
            }  	
        }, 0L, 20L);

	}
	
	public void stop() {
		this.scheduler.cancelTasks(Lootdrop.getInstance());
	}
	
}
