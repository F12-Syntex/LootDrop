package com.lootdrop.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lootdrop.main.Lootdrop;
import com.lootdrop.utils.ComponentBuilder;
import com.lootdrop.utils.Luck;
import com.lootdrop.utils.MessageUtils;

public class Crates {

	public static List<ItemStack> getContents() {
		
		FileConfiguration config = Lootdrop.getInstance().configManager.getConfig("settings").getConfig();
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		config.getConfigurationSection("Crate").getKeys(false).forEach(i -> {
			
			ConfigurationSection section = config.getConfigurationSection("Crate." + i);
			
			double perc = section.getDouble("Chance");
			
			if(Luck.chance(perc)) {
			
				Material material = null;
				
				try {
					material = Material.valueOf(section.getString("Material"));
				}catch (Exception e) {
					MessageUtils.consolePrint("&c" + section.getString("Material") + " is not a valid material in crate section " + i);
				}
				
				int amount = section.getInt("Amount");
				List<String> lore = ComponentBuilder.createLore(section.getStringList("Lore"));
			
				ItemStack item = new ItemStack(material, amount);
				ItemMeta meta = item.getItemMeta();
				meta.setLore(lore);
				
				
				for(Map<?, ?> map : section.getMapList("Enchantments")) {
					int level = Integer.valueOf(map.keySet().toArray()[0].toString());
					String enchant = map.get(map.keySet().toArray()[0]).toString();
					
					Enchantment ench = null;
					for(Enchantment v : Enchantment.values()) {
						if(enchant.equalsIgnoreCase(v.getName())) {
							ench = v;
							break;
						}
					}
					
					if(ench == null) {
						MessageUtils.consolePrint("&c" + enchant + " is not a valid material in crate section " + i);
						return;
					}
					
					item.addUnsafeEnchantment(ench, level);
					meta.addEnchant(ench, level, true);
					
				}
				
				if(!section.getString("Name").equalsIgnoreCase("%item%")) {
					meta.setDisplayName(section.getString("Name").replace("%item%", material.name()));
				}
				
				
				item.setItemMeta(meta);
				
				items.add(item);
				
			}
			
		});
		
		return items;
		
	}
	
	
}
