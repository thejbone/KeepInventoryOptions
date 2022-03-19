package com.chillaxmc.keepinventorypve.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Cooldown {
    private HashMap<Player, Long> cache;
    private int delay;

    public Cooldown(int delay) {
        this.delay = delay;
        this.cache = new HashMap<>();
    }

    public void addPlayer(Player player) {
        cache.put(player, now() + delay);
    }

    public long getPlayerDelay(Player player) {  return (cache.get(player) - now()); }

    public String getPlayerDelayFormatted(Player player){
        long delay = getPlayerDelay(player);
        return (delay / 60) + " mins and " + (delay % 60) + " secs";
    }

    public boolean isValid(Player player) {
        return !cache.containsKey(player) || cache.get(player) < now();
    }

    public int getDelay() {
        return delay;
    }

    private long now() {
        return System.currentTimeMillis() / 1000L;
    }
}
