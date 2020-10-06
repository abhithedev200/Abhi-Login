package com.abhiram.abhilogin;

import com.abhiram.abhilogin.api.bstats.Metrics;
import com.abhiram.abhilogin.command.ChangePasswordCommand;
import com.abhiram.abhilogin.command.UnRegisterCommand;
import com.abhiram.abhilogin.event.PlayerControlEvent;
import com.abhiram.abhilogin.event.SessionListener;
import com.abhiram.abhilogin.file.Config;
import com.abhiram.abhilogin.file.MessageConfig;
import com.abhiram.abhilogin.util.SpigotUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public Config config;
    public MessageConfig messageConfig;
    public static String plugin_version = "1.9";

    @Override
    public void onEnable()
    {
        instance = this;
        config = new Config();
        messageConfig = new MessageConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerControlEvent(),this);
        Bukkit.getPluginManager().registerEvents(new SessionListener(),this);
        this.getCommand("changepw").setExecutor(new ChangePasswordCommand());
        this.getCommand("unregister").setExecutor(new UnRegisterCommand());
        new SpigotUpdater().Check();
        new Metrics(this,9042);
    }

    public static Main getInstance()
    {
        return instance;
    }
}
