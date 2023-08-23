package org.xdm8.cards;

import org.xdm8.cards.items.fire;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class giveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            return false;
        } else {
            if (sender instanceof Player p) {
                if (Objects.equals(args[0], "fire")) {
                    fire fire = new fire();
                    p.getInventory().addItem(fire.getItem());
                } else {
                    p.sendMessage("No Such Card,");
                }
            } else {
                sender.sendMessage("Non-Valid Sender Type.");
            }
        }
        return true;
    }
}
