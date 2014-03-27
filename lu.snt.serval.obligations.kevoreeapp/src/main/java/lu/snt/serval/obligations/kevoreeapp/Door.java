package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.DoorLockAction;
import lu.snt.serval.obligations.framework.DoorStatus;
import org.kevoree.annotation.*;

import java.util.Date;

@ComponentType
@Library(name = "Java")
public class Door {

    protected DoorStatus ds= DoorStatus.LOCKED;


    @Output
    org.kevoree.api.Port status;

    @Input
    public void command(Object i) {
        try
        {
            DoorLockAction da = (DoorLockAction) i;
            if(da== DoorLockAction.OPENDOOR){
                ds=DoorStatus.OPENED;
                Date now=new Date();
                String s= "Door is opened at "+now.toString();
                org.kevoree.log.Log.info("[Door] "+s);
                status.send(s);
            }
            if(da== DoorLockAction.CLOSEDOOR){
                ds=DoorStatus.LOCKED;
                Date now=new Date();
                String s= "Door is opened at "+now.toString();
                org.kevoree.log.Log.info("[Door] "+s);
                status.send(s);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());

        }

    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



