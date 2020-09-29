package com.abhiram.abhilogin.event;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.encrypt.EncryptType;
import com.abhiram.abhilogin.login.PlayerLoginManager;
import com.abhiram.abhilogin.util.TitileReflection;
import com.abhiram.abhilogin.util.Util;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.HashMap;

public class Test implements Listener {
    private HashMap<Player,Boolean> registering = new HashMap<Player, Boolean>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if(registering.containsKey(e.getPlayer()) && registering.get(e.getPlayer())) {
            new PlayerLoginManager().StartRegister(e.getPlayer(),e.getMessage());
            e.getPlayer().sendMessage("Hey you have been registered sucess Fully!");
            registering.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e)
    {
        if(!Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            registering.put(e.getPlayer(),true);

        }
    }
    
}
