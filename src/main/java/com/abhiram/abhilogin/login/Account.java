package com.abhiram.abhilogin.login;

public class Account {
    private Boolean ispremium;
    private String uuid;
    private Boolean Loginstatus;
    private String password;

    public Account(String uuid,Boolean ispremium,String password)
    {
        this.uuid = uuid;
        this.ispremium = ispremium;
        this.password = password;
        this.Loginstatus = false;
    }

    public void SetPassword(String newPassword)
    {
        this.password = newPassword;
    }

    public void SetLoginStatus(Boolean loginstatus)
    {
        this.Loginstatus = loginstatus;
    }


    public void SetPremium(Boolean ispremium)
    {
        this.ispremium = ispremium;
    }


    public String getUuid()
    {
        return this.uuid;
    }

    public Boolean Ispremium()
    {
        return ispremium;
    }

    public String getPassword()
    {
        return this.password;
    }

    public Boolean getLoginstatus()
    {
        return this.Loginstatus;
    }
}
