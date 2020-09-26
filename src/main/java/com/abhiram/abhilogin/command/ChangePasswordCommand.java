package com.abhiram.abhilogin.command;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.encrypt.Encrypt;
import com.abhiram.abhilogin.file.PlayerData;
import com.abhiram.abhilogin.login.Account;
import com.abhiram.abhilogin.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePasswordCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage("Hey you can't run this command on console!");
            return true;
        }

        Player p = (Player) commandSender;

        Account account = Util.getPlayerLoginManager().getPlayerAccount(p);

        if(args.length == 0)
        {
            p.sendMessage(ChatColor.RED + "Invalid usage of command correct usage /changepw yournewpassword!");
            return true;
        }
        account.SetPassword(new Encrypt(args[0],Util.getEncyptionType()).getEncryptedPassword());
        PlayerData playerData = new PlayerData(Main.getInstance(),account.getUuid());
        playerData.getConfig().set("password",account.getPassword());
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().messageConfig.getConfig().getString("Change-password")));
        return true;
    }
}
