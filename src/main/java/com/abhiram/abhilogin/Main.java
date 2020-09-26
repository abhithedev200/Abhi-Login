package com.abhiram.abhilogin;

import com.abhiram.abhilogin.command.ChangePasswordCommand;
import com.abhiram.abhilogin.event.PlayerControlEvent;
import com.abhiram.abhilogin.file.Config;
import com.abhiram.abhilogin.file.MessageConfig;
import com.abhiram.abhilogin.util.SpigotUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public Config config;
    public MessageConfig messageConfig;
    public static String plugin_version = "1.5";

    @Override
    public void onEnable()
    {
        instance = this;
        config = new Config();
        messageConfig = new MessageConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerControlEvent(),this);
        this.getCommand("changepw").setExecutor(new ChangePasswordCommand());
        new SpigotUpdater().Check();
    }

    public static Main getInstance()
    {
        return instance;
    }
}
