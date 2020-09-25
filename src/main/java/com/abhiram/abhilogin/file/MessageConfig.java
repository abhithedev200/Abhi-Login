package com.abhiram.abhilogin.file;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.util.Util;

public class MessageConfig extends AbstractFile {

    public MessageConfig() {
        super(Main.getInstance(),"messages.yml","", true);
        Util.Log("Makeing Messages.yml");
    }
}
