
package com.lootdrop.commands;

import org.bukkit.entity.Player;

import com.lootdrop.main.Lootdrop;
import com.lootdrop.utils.MessageUtils;

public class Help extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	MessageUtils.sendHelp(player);
    	
    	Lootdrop.getInstance().drops.dropCrates();
    	
    }

    @Override

    public String name() {
        return "help";
    }

    @Override
    public String info() {
        return "displays the help command";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.DEFAULT;	
	}
	

}