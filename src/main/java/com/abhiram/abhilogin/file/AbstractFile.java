package com.abhiram.abhilogin.file;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.util.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AbstractFile{
    private Main plugin;
    private File file;
    protected FileConfiguration configuration;
    protected Boolean save;


    public AbstractFile(Main main,String filename,String datafolder,Boolean save)
    {
        this.plugin = main;
        File file1 = new File(main.getDataFolder() + datafolder);

        if(!file1.exists())
        {
            file1.mkdirs();
        }

        file = new File(file1,filename);
        if(!file.exists())
        {
            if(save)
            {
                Main.getInstance().saveResource(filename,false);
                configuration = YamlConfiguration.loadConfiguration(file);
                return;
            }

            try
            {
                file.createNewFile();
            }catch (Exception exp)
            {
                exp.printStackTrace();
            }
        }

        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void save(){
        try{
            configuration.save(file);
        }catch(IOException e){
            Util.Log("Unable to Save Config File!");
        }
    }
    public FileConfiguration getConfig(){
        return configuration;
    }

    public void reload(){
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void delete() {
        file.delete();
    }
}
