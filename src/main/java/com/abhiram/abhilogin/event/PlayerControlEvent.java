package com.abhiram.abhilogin.event;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.login.Account;
import com.abhiram.abhilogin.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class PlayerControlEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e)
    {
        if(!Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            // Send Message To Player
            for(String str : Main.getInstance().messageConfig.getConfig().getStringList("Player-register-message"))
            {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',str));
            }
            return;
        }


        if(!Util.getPlayerLoginManager().getPlayerAccount(e.getPlayer()).getLoginstatus())
        {
            for(String str : Main.getInstance().messageConfig.getConfig().getStringList("Player-login-message"))
            {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',str));
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        if(Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            Account account = Util.getPlayerLoginManager().getPlayerAccount(e.getPlayer());
            if(Util.getPlayerLoginManager().VerifyPassword(e.getMessage(),account.getPassword(),Util.getEncyptionType()))
            {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getInstance().messageConfig.getConfig().getString("Login-success")));
                account.SetLoginStatus(true);
                return;
            }
            else
            {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getInstance().messageConfig.getConfig().getString("Invalid-Password")));
                return;
            }
        }

        if(!Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            e.setCancelled(true);
            Util.getPlayerLoginManager().StartRegister(e.getPlayer(),e.getMessage());
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getInstance().messageConfig.getConfig().getString("Register-done-message")));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        if(!Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            if(Main.getInstance().config.getConfig().getBoolean("Disable-Player-Movement"))
            {
                e.setCancelled(true);
            }
            return;
        }

        if(!Util.getPlayerLoginManager().getPlayerAccount(e.getPlayer()).getLoginstatus())
        {
            if(Main.getInstance().config.getConfig().getBoolean("Disable-Player-Movement"))
            {
                e.setCancelled(true);
            }
            return;
        }
    }


    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent e)
    {
        if(!Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            e.setCancelled(true);
            return;
        }

        if(!Util.getPlayerLoginManager().getPlayerAccount(e.getPlayer()).getLoginstatus())
        {
            e.setCancelled(true);
            return;
        }
    }


    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent e)
    {
        if(!Util.getPlayerLoginManager().isPlayerRegistered(e.getPlayer()))
        {
            e.setCancelled(true);
            return;
        }

        if(!Util.getPlayerLoginManager().getPlayerAccount(e.getPlayer()).getLoginstatus())
        {
            e.setCancelled(true);
            return;
        }
    }


}
