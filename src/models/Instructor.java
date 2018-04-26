package models;

import java.io.Serializable;

public class Instructor extends User implements Serializable
{

    private final String fName;
    private final String lName;
    private final String usrName;
    private final String passwrd;
    private final String emplNumber;

    public Instructor(String first, String last, String user, String pass, String empNum)
    {
        super(first, last, user, pass);
        fName = first;
        lName = last;
        usrName = user;
        passwrd = pass;
        emplNumber = empNum;
    }

    @Override
    public boolean authenticate(String uName, String uPassword)
    {
        String name = uName;
        String password = uPassword;
        boolean result = false;
        if((name.equals(usrName)) && (password.equals(passwrd)))
        {
            result = true;
        }
        else;

        return result;
    }

    public String getInstructorName()
    {
        String result = (fName + " " + lName);
        return result;
    }
    
    public String getInstructorUsername() {
        return usrName;
    }
    
    public String getInstructorFirstName(){
        return fName;
    }

}
