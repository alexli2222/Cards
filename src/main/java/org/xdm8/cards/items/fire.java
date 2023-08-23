package org.xdm8.cards.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class fire {
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + "Fire Card");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Shoots a fireball.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
