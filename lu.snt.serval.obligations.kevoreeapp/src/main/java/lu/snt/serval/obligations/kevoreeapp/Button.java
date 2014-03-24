package lu.snt.serval.obligations.kevoreeapp;

import org.kevoree.annotation.*;
import sun.rmi.log.ReliableLog;

import java.util.Date;

@ComponentType
@Library(name = "Java")
public class Button {


    @Output
    org.kevoree.api.Port signal;

    public void pressButton(){
        Date now = new Date();
        String s= "Button pressed at "+ now.toString();
        org.kevoree.log.Log.info(s);
        signal.send(s);
    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {}

}



