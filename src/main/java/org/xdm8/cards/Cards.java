package org.xdm8.cards;

import org.xdm8.cards.abilities.fireAbility;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

public final class Cards extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        //Listeners
        log("Attempting to initialize Cards plugin...");
        log("Attempting to register event handlers...");
        log("Attempting to register Fire Card Ability...");
        getServer().getPluginManager().registerEvents(new fireAbility(), this);
        log("Successfully registered Fire Card Ability.");
        log("Successfully registered all event handlers.");
        //Commands
        log("Attempting to register commands...");
        log("Attempting to register giveCard command...");
        Objects.requireNonNull(getCommand("giveCard")).setExecutor(new giveCommand());
        log("Successfully registered giveCard command.");
        log("Successfully registered all commands.");
        log("Cards plugin successfully initialized.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log("Executing Cards plugin shutdown behavior...");
        if (getDataFolder().exists()) {
            log("Plugin folder already exists, skipping creation.");
            File logFolder = new File(getDataFolder() + File.separator + "logs");
            if (logFolder.exists()) {
                log("Logs folder already exists, skipping creation.");
            } else {
                log("Logs folder doesn't seem to exist, attempting to create log folder...");
                if (logFolder.mkdirs()) {
                    log("Successfully created logs folder.");
                } else {
                    log("Creation of logs folder was unsuccessful, plugin logs will not be saved.");
                }
            }
        } else {
            log("Plugin folder doesn't seem to exist, attempting to create log folder...");
            if (getDataFolder().mkdirs()) {
                log("Successfully created plugin folder.");
                File logFolder = new File(getDataFolder() + File.separator + "logs");
                if (logFolder.exists()) {
                    log("Logs folder already exists, skipping creation.");
                    try {
                        saveLogs(new Logger().logs);
                    } catch (IOException e) {
                        log("Unable to write logs file. (IOException)");
                    }
                } else {
                    log("Logs folder doesn't seem to exist, attempting to create log folder...");
                    if (logFolder.mkdirs()) {
                        log("Successfully created logs folder.");
                        try {
                            saveLogs(new Logger().logs);
                        } catch (IOException e) {
                            log("Unable to write logs file. (IOException)");
                        }
                    } else {
                        log("Creation of logs folder was unsuccessful, plugin logs will not be saved.");
                    }
                }
            } else {
                log("Creation of plugin folder was unsuccessful, no information will be saved.");
            }
        }
        log("Cards plugin successfully shut down");
    }
    public void log(String text) {
        System.out.println("[Cards] " + text);
    }
    private void saveLogs(Map<Timestamp, String> sessionLogs) throws IOException {
        Logger logger = new Logger();
        log("Attempting to create plugin logs file for current session");
        File logsFile = new File(getDataFolder() + File.separator + "logs", logger.currentTimestamp() +".txt");
        if (!logsFile.exists()) {
            if (logsFile.createNewFile()) {
                log("Successfully created logs file for this current session.");
            } else {
                log("This session's log file wasn't able to be created.");
            }
        } else {
            log("This session's log file was already found? Failed to create file.");
        }
        log("Attempting to write this session's logs in file.");
        try (FileWriter writer = new FileWriter(logsFile)){
            log("Successfully entered file as FileWriter, writing logs...");
            for (Timestamp key : sessionLogs.keySet()) {
                writer.write("<"+key.toString()+"> " + sessionLogs.get(key)+"\n");
            }
            writer.write("<"+logger.currentTimestamp() +"> Server Closed\nEnd of Logs");
            writer.flush();
            log("Log file successfully written.");
        } catch (IOException e) {
            log("Unable to write in logs file.");
        }
    }
}
