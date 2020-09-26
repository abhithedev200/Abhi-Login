package com.abhiram.abhilogin.util;

import com.abhiram.abhilogin.Main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SpigotUpdater {


    public void Check()
    {
        try {
            if (checkPluginUpdate()) {
                Util.Log("New Update Found., Please update the plugin!");
                Util.Log("Link: https://www.spigotmc.org/resources/abhi-login.84216/");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Boolean checkPluginUpdate() throws Exception
    {
        URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=84216/");

        InputStream is = url.openStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String version = reader.readLine();
        reader.close();


        if (!version.equalsIgnoreCase(Main.plugin_version))
        {
            return true;
        }
        return false;
    }
}
