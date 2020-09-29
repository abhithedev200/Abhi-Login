package com.abhiram.abhilogin.event;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.login.Session;
import com.abhiram.abhilogin.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SessionListener implements Listener {


    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e)
    {
        Session ses = getPlayerSession(e.getPlayer());

        if(ses != null)
        {
           if(Main.getInstance().config.getConfig().getBoolean("Session"))
           {
               e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getInstance().messageConfig.getConfig().getString("Session-Login")));
               Util.getPlayerLoginManager().getPlayerAccount(e.getPlayer()).SetLoginStatus(true);
           }
        }
    }

    public Session getPlayerSession(Player p)
    {
        for(Session session : Session.sessions)
        {
            if(session.getPlayer_ip().getAddress().getHostAddress().equalsIgnoreCase(p.getAddress().getAddress().getHostAddress()))
            {
                Util.Log("Player Session Matched!");
                return session;
            }

            Util.Log("Session Not matched!");
        }

        return null;
    }
}
