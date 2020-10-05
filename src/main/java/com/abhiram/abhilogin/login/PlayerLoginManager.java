package com.abhiram.abhilogin.login;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.encrypt.Encrypt;
import com.abhiram.abhilogin.encrypt.EncryptType;
import com.abhiram.abhilogin.file.PlayerData;
import com.abhiram.abhilogin.util.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class PlayerLoginManager {
    private ArrayList<Account> accounts = new ArrayList<Account>();

    public PlayerLoginManager()
    {
        loadAccounts();
    }

    public Boolean VerifyPassword(String password, String hash, EncryptType encryptType)
    {
        return new Encrypt(password,encryptType).getEncryptedPassword().equalsIgnoreCase(hash);
    }

    public void StartRegister(Player p,String password)
    {
        Account account = new Account(p.getUniqueId().toString(),false,new Encrypt(password, Util.getEncyptionType()).getEncryptedPassword());
        PlayerData data = new PlayerData(Main.getInstance(),p.getUniqueId().toString());
        data.getConfig().set("password",account.getPassword());
        data.getConfig().set("premium",account.Ispremium());
        data.save();
        accounts.add(account);
    }


    private void loadAccounts()
    {
        FileConfiguration config;
        File file = new File(Main.getInstance().getDataFolder() + "/" + "Playerdata");
        if(!file.exists())
        {
            file.mkdirs();
        }
        for(File playerdata : file.listFiles())
        {
            config = YamlConfiguration.loadConfiguration(playerdata);
            Account account = new Account(playerdata.getName().replaceAll(".yml",""),config.getBoolean("premium"),config.getString("password"));
            accounts.add(account);
        }
    }

    public Boolean isPlayerRegistered(Player p)
    {
        for(Account account : accounts)
        {
            if(account.getUuid().equalsIgnoreCase(p.getUniqueId().toString()))
            {
                return true;
            }
        }

        return false;
    }

    public Account getPlayerAccount(Player p)
    {
        for(Account acc : accounts) {
            if (acc.getUuid().equalsIgnoreCase(p.getUniqueId().toString()))
            {
                return acc;
            }
        }

        return null;
    }

    public ArrayList<Account> getAccounts()
    {
        return this.accounts;
    }
}
