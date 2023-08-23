package org.xdm8.cards.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.xdm8.cards.items.fire;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class fireAbility implements Listener {
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void FireAbility(PlayerInteractEvent e) {
        fire fire = new fire();
        if (Objects.equals(e.getItem(), fire.getItem())) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                if (fireAbilityReady(e.getPlayer())) {
                    Fireball fireball = e.getPlayer().launchProjectile(Fireball.class);
                    Vector direction = e.getPlayer().getLocation().getDirection();
                    fireball.setDirection(direction);
                    fireball.setVelocity(direction.multiply(3));
                    fireball.setYield(6);
                    cooldowns.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
                } else {
                    e.getPlayer().sendMessage(ChatColor.RED+"This ability is still on cooldown for "+(fireAbilityLeft(e.getPlayer())/1000)+" seconds!");
                }
            }
        }
    }
    private boolean fireAbilityReady(Player player) {
        UUID playerId = player.getUniqueId();
        if (cooldowns.containsKey(playerId)) {
            long lastUseTime = cooldowns.get(playerId);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUseTime;
            return elapsedTime >= 5000;
        }
        return true;
    }

    private float fireAbilityLeft(Player player) {
        UUID playerId = player.getUniqueId();
        long lastUseTime = cooldowns.get(playerId);
        long currentTime = System.currentTimeMillis();
        return (float) (5000 - currentTime + lastUseTime);
    }
}
