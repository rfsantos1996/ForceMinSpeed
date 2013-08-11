package com.jabyftw.forceminspeed;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ForceMinSpeed extends JavaPlugin implements Listener {
    int delay;
    int foodLevel;
    boolean intervalCheck;
    
    @Override
    public void onEnable() {
        config();
        getLogger().info("Configured!");
        
        getServer().getPluginManager().registerEvents(this, this);
        if(intervalCheck)
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    Player[] players = getServer().getOnlinePlayers();
                    for(Player player : players)
                        player.setFoodLevel(foodLevel);
                }
            }, 40L, delay);
        getLogger().info("Enabled!");
    }
    
    @Override
    public void onDisable() {
        if(intervalCheck) {
            getServer().getScheduler().cancelTasks(this);
            getLogger().info("Unregistered Tasks!");
        }
    }
    
    public void config() {
        FileConfiguration config = getConfig();
        config.addDefault("config.foodLevel", 6);
        config.addDefault("invervalCheck.delayInTicks", 60);
        config.addDefault("invervalCheck.enabled", false);
        config.options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        foodLevel = config.getInt("config.foodLevel");
        delay = config.getInt("invervalCheck.delayInTicks");
        intervalCheck = config.getBoolean("invervalCheck.enabled");
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setFoodLevel(foodLevel);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
        e.getPlayer().setFoodLevel(foodLevel);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().setFoodLevel(foodLevel);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent e) {
        e.getPlayer().setFoodLevel(foodLevel);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        e.getPlayer().setFoodLevel(foodLevel);
    }
}
