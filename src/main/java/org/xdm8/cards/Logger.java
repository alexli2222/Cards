package org.xdm8.cards;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Logger implements Listener {
    Map<Timestamp, String> logs = new HashMap<>();
    public void loggerAdd(Timestamp time, String line) {
        logs.put(time, line);
    }
    public Timestamp currentTimestamp() {
        return Timestamp.from(Instant.ofEpochMilli(System.currentTimeMillis()));
    }
    @EventHandler
    public void onCommandUse(PlayerCommandSendEvent e) {
        System.out.println("Command found, putting in logs!");
        loggerAdd(currentTimestamp(), e.getPlayer().getName() + " executed command " + e.getCommands());
    }
}
