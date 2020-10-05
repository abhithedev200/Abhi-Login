package com.abhiram.abhilogin.command;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.file.PlayerData;
import com.abhiram.abhilogin.login.Account;
import com.abhiram.abhilogin.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnRegisterCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage(ChatColor.RED + "You can't run this command on console!");
            return true;
        }

        Player p = (Player) commandSender;

        if(!p.hasPermission("abhilogin.admin"))
        {
            p.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
            return true;
        }

        if(args.length == 0)
        {
            p.sendMessage(ChatColor.RED + "Invalid usage of command correct usage /unregister PlayerName");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if(target == null)
        {
            p.sendMessage(ChatColor.RED + "That player you mentioned not found!");
            return true;
        }

        unRegister(target);
        p.sendMessage(ChatColor.YELLOW + "You have unregistered " + target.getName());

        target.kickPlayer("You have been unregistered by " + p.getName());
        return true;
    }

    private void unRegister(Player p)
    {
        Account acc = Util.getPlayerLoginManager().getPlayerAccount(p);

        if(acc != null)
        {
            Util.getPlayerLoginManager().getAccounts().remove(acc);
            PlayerData data = new PlayerData(Main.getInstance(),p.getUniqueId().toString());
            data.delete();
        }
    }
}
