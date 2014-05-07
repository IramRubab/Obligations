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
        String s="Password changed at "+now.toString()+" to "+ password;
        org.kevoree.log.Log.info("[DoorLoc] "+ s);
        doorComand.send(DoorLockAction.CLOSEDOOR);

    }

    @Input
    public void openDoor(Object i){
        String password = (String) i;

        String s = "";

        //org.kevoree.log.Log.info(password+ "/"+pass);

        if(password.equals(pass))
        {
            doorComand.send(DoorLockAction.OPENDOOR);
            s="Door unlocked";

        }
        else
        {
            s="The password is incorrect";
        }
        org.kevoree.log.Log.info("[DoorLoc] "+s);
        //return s;
    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



