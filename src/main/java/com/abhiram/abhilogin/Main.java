package com.abhiram.abhilogin;

import com.abhiram.abhilogin.event.PlayerControlEvent;
import com.abhiram.abhilogin.file.Config;
import com.abhiram.abhilogin.file.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public Config config;
    public MessageConfig messageConfig;

    @Override
    public void onEnable()
    {
        instance = this;
        config = new Config();
        messageConfig = new MessageConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerControlEvent(),this);
    }

    public static Main getInstance()
    {
        return instance;
    }
}
