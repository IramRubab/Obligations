package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.DoorLockAction;
import org.kevoree.annotation.*;

import java.util.Date;

@ComponentType
@Library(name = "Java")
public class DoorLock {

    @Param(defaultValue = "Default Content")
    String pass="1234";


    @Output
    org.kevoree.api.Port doorComand;

    @Input
    public void changeCode(String password){
        pass=password;
        Date now = new Date();
        String s="Password changed at "+now.toString();
        org.kevoree.log.Log.info(s);

    }

    public String openDoor(String password ){
        String s = "";

        if(password.equals(pass))
        {
            doorComand.send(DoorLockAction.OPENDOOR);
            s="Door unlocked";

        }
        else
        {
            s="The password is incorrect";
        }
        org.kevoree.log.Log.info(s);
        return s;

    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



