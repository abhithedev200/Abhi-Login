package com.abhiram.abhilogin.file;

import com.abhiram.abhilogin.Main;

public class PlayerData extends AbstractFile {


    public PlayerData(Main plugin,String uuid) {
        super(plugin,uuid,"/Playerdata",false);
    }
}
