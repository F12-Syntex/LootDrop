
package com.lootdrop.commands;

import org.bukkit.entity.Player;

import com.lootdrop.main.Lootdrop;
import com.lootdrop.utils.MessageUtils;

public class Reload extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	Lootdrop.getInstance().configManager.reload();
    	player.sendMessage(messages.PREFIX + " " + MessageUtils.translateAlternateColorCodes("&6reloaded!"));
    }

    @Override

    public String name() {
        return "reload";
    }

    @Override
    public String info() {
        return "reloads the plugin.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.ADMIN;	
	}
	

}