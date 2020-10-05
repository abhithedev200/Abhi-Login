package com.abhiram.abhilogin.login;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Session implements Runnable{
    public static ArrayList<Session> sessions = new ArrayList<>();
    private InetSocketAddress player_ip;

    public Session(InetSocketAddress ip)
    {

        this.player_ip = ip;
        sessions.add(this);
    }


    public InetSocketAddress getPlayer_ip()
    {
        return this.player_ip;
    }



    public void run()
    {
        sessions.remove(this);
    }
}
