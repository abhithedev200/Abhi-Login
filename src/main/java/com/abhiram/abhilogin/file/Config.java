package com.abhiram.abhilogin.file;

import com.abhiram.abhilogin.Main;

public class Config extends AbstractFile{

    public Config() {
        super(Main.getInstance(),"config.yml","",true);
    }
}
