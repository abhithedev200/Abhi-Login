package com.abhiram.abhilogin.util;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.encrypt.EncryptType;
import com.abhiram.abhilogin.login.PlayerLoginManager;

public class Util {
    private static PlayerLoginManager playerLoginManager;

    public static void Log(String message)
    {
       Main.getInstance().getLogger().info(message);
    }

    public static PlayerLoginManager getPlayerLoginManager()
    {
        if(playerLoginManager == null)
        {
            playerLoginManager = new PlayerLoginManager();
        }

        return playerLoginManager;
    }

    public static EncryptType getEncyptionType()
    {
        switch (Main.getInstance().config.getConfig().getString("Encrypt-Type"))
        {
            case "MD5":
                return EncryptType.MD5;
            case "SHA256":
                return EncryptType.SHA256;
            case "SHA512":
                return EncryptType.SHA512;
            case "BCRYPT":
                return EncryptType.BCRYPT;
        }

        Log("Hey, invalid Encryption Type found in config.yml using default EncryptionType (MD5)");
        return EncryptType.MD5;
    }
}
