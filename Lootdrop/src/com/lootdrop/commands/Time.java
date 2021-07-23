
package com.lootdrop.commands;

import org.bukkit.entity.Player;

import com.lootdrop.main.Lootdrop;
import com.lootdrop.placeholder.time.TimeFormater;
import com.lootdrop.utils.MessageUtils;

public class Time extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	int time = Lootdrop.getInstance().mainTimer.getTimer();
    	
    	TimeFormater formatter = new TimeFormater();
    	
    	MessageUtils.inform(player, " &6Theres " + formatter.parse(time) + " &6before the next drop.");
    }

    @Override

    public String name() {
        return "time";
    }

    @Override
    public String info() {
        return "Displays the time needed before a drop.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.TIME;	
	}
	

}