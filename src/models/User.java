package models;

import java.io.Serializable;

abstract class User implements Serializable
{

    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    User(String fName, String lName, String uName, String pswd)
    {
        firstName = fName;
        lastName = lName;
        userName = uName;
        password = pswd;

    }

    abstract boolean authenticate(String name, String password);

}