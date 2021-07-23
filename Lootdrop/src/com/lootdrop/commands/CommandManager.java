package com.lootdrop.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lootdrop.cooldown.CooldownUser;
import com.lootdrop.cooldown.Cooldowns;
import com.lootdrop.data.ConfigData;
import com.lootdrop.main.Lootdrop;
import com.lootdrop.tags.TagFactory;
import com.lootdrop.utils.MessageUtils;

public class CommandManager extends ConfigData implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();

    private Lootdrop plugin;

    //Sub Commands
    public String main = "lootdrop";
    
    public void setup(Lootdrop plugin) {
    	this.setPlugin(plugin);
    	plugin.getCommand(main).setExecutor(this);
        commands.add(new Help());
        commands.add(new Reload());
        commands.add(new SetSpawn());
        commands.add(new Time());
    }


    public ArrayList<SubCommand> getCommands(){
    	return commands;
    }

    
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {

            sender.sendMessage(messages.INAVLID_ENTITY);

            return true;

        }

        Player player = (Player) sender;
        
    	try {

        if (command.getName().equalsIgnoreCase(main)) {

            if (args.length == 0) {
            	
            	CooldownUser user = Lootdrop.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	SubCommand cmd = new Help();
            	
            	if(!player.hasPermission(cmd.permission())) {
		    		MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
		    		return true;
		        }
            	
            	
            	String key = cmd.name();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.BYPASS)) {
            	
            		cmd.onCommand(player, args);
            	  
                	user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(Cooldowns.instance().message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendMessage(player, tagHelper.parse());
                }
            	
                return true;

            }

            SubCommand target = this.get(args[0]);

            if (target == null) {

                player.sendMessage(messages.INVALID_SYNTEX);

                return true;

            }
            
		    if(!player.hasPermission(target.permission())) {
		    		MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
		    		return true;
		    }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));

            arrayList.remove(0);
            
            try{
            	
            	CooldownUser user = Lootdrop.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	String key = args[0].trim();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.BYPASS)) {
            		
            	    target.onCommand(player, args);
            	    
            	    user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(Cooldowns.instance().message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendMessage(player, tagHelper.parse());
                }
            	
            
            
            }catch (Exception e){
                player.sendMessage(messages.ERROR);
                e.printStackTrace();
            }

        }


    }catch(Throwable e) {
        player.sendMessage(messages.ERROR);
        e.printStackTrace();
    }

        return true;
    
    }



    private SubCommand get(String name) {

        Iterator<SubCommand> subcommands = commands.iterator();

        while (subcommands.hasNext()) {

            SubCommand sc = (SubCommand) subcommands.next();


            if (sc.name().equalsIgnoreCase(name)) {

                return sc;

            }


            String[] aliases;

            int length = (aliases = sc.aliases()).length;



            for (int var5 = 0; var5 < length; ++var5) {

                String alias = aliases[var5];

                if (name.equalsIgnoreCase(alias)) {

                    return sc;

                }

            }

        }

        return null;

    }


	public Lootdrop getPlugin() {
		return plugin;
	}


	public void setPlugin(Lootdrop plugin) {
		this.plugin = plugin;
	}

}