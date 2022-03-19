package com.chillaxmc.keepinventorypve;

import com.chillaxmc.keepinventorypve.listeners.playerdeath;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class KeepInventoryPvE extends JavaPlugin {

    private String DISABLE_KEEPINV_PERMISSION = "keepInvOptions.disableForPlayer";
    public static final Logger LOGGER = Logger.getLogger("Minecraft");



    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new playerdeath(), this);
        LOGGER.info("KeepInvOptions Starting...");
    }

    @Override
    public void onDisable() {
    }






}
