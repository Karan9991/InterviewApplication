package com.pritesh.interviewapplication.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by pritesh.patel on 2017-08-02, 9:39 AM.
 * ADESA, Canada
 */

public class User extends RealmObject
{
    @Required
    private String userName;
    @Required
    @PrimaryKey
    private String userEmail;
    @Required
    private String userPassword;

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getUserPassword()
    {
        return this.userPassword;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getUserEmail()
    {
        return this.userEmail;
    }
}
