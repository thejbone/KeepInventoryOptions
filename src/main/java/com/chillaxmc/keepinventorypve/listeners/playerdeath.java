package com.chillaxmc.keepinventorypve.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Locale;


public class playerdeath implements Listener {

    public final String PERMS_KEEPONPVP = "KeepInventoryPvE.keepOnPvP";
    public final String PERMS_IGNORE = "KeepInventoryPvE.ignore";
    public final String PERMS_LOSEEXP = "KeepInventoryPvE.loseEXP";

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        String deathMsg = e.getDeathMessage();
        if(player != null){
            if(player.getKiller() != null) {
                if(player.hasPermission(PERMS_KEEPONPVP)){
                    e.setDeathMessage(ChatColor.GOLD + "[" + ChatColor.RED + "PvP" + ChatColor.GOLD + "] " + ChatColor.RED + e.getEntity().getDisplayName() + ChatColor.WHITE + " was killed by " + ChatColor.RED + e.getEntity().getKiller().getDisplayName() + ChatColor.WHITE + "!");
                    e.setKeepInventory(true);
                    if(!player.hasPermission(PERMS_LOSEEXP)){
                        e.setKeepLevel(true);
                    }
                    e.getDrops().clear();
                } else {
                    e.setDeathMessage(ChatColor.GOLD + "[" + ChatColor.RED + "PvP" + ChatColor.GOLD + "] " + ChatColor.RED + e.getEntity().getDisplayName() + ChatColor.WHITE + " was killed by " + ChatColor.RED + e.getEntity().getKiller().getDisplayName() + ChatColor.WHITE + " and lost their inventory!");
                    e.setKeepInventory(false);
                    e.setKeepLevel(false);
                }
            }
            else if(player.hasPermission(PERMS_IGNORE) && !player.hasPermission(PERMS_KEEPONPVP)) {
                e.setKeepInventory(false);
                e.setDeathMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Death" + ChatColor.GOLD + "] " + ChatColor.RED + deathMsg + " and lost their inventory!");

            } else {
                e.setKeepInventory(true);
                if(!player.hasPermission(PERMS_LOSEEXP)){
                    e.setKeepLevel(true);
                }
                e.getDrops().clear();
                e.setDeathMessage(ChatColor.GOLD + "[" + ChatColor.RED + "Death" + ChatColor.GOLD + "] " + ChatColor.RED + deathMsg);
            }
            if(player.getWorld().getName().toLowerCase(Locale.ROOT).contains("dxl")){
                e.setDeathMessage(e.getDeathMessage() + ChatColor.AQUA + " [Dungeon death! /play]");
            }
        }
    }
}
