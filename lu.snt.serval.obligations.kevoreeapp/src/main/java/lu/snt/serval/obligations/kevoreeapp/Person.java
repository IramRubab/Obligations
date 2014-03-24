package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonAction;
import lu.snt.serval.obligations.framework.SMS;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;
import java.util.Timer;


import java.util.Random;
import java.util.TimerTask;

@ComponentType
@Library(name = "Java")
public class Person {

    public String name;
    public PersonAction behavior;
    public int waitTime=6000;
    public String phoneNb="";
    private static Random random = new Random();


    @Output
    org.kevoree.api.Port sendSms;

    @Input
    public void receiveSMS(Object i) {
        final SMS msg=(SMS) i;
        SMS tosend;
        if(((SMS) i).getTo().equals(phoneNb))
        {
            if(behavior==PersonAction.SENDYES){
                tosend = new SMS(phoneNb,msg.getFrom(),"yes");
                Log.info("Person "+name+ " sending yes");
                sendSms.send(tosend);
                return;
            }
            else if(behavior==PersonAction.SENDNO){
                tosend = new SMS(phoneNb,msg.getFrom(),"no");
                Log.info("Person "+name+ " sending no");
                sendSms.send(tosend);
                return;
            }
            else if(behavior==PersonAction.SENDRANDOM){
                if(random.nextBoolean())
                {
                    tosend = new SMS(phoneNb,msg.getFrom(),"yes");
                    Log.info("Person "+name+ " sending yes");
                    sendSms.send(tosend);
                    return;
                }
                else
                {
                    tosend = new SMS(phoneNb,msg.getFrom(),"no");
                    Log.info("Person "+name+ " sending no");
                    sendSms.send(tosend);
                    return;
                }
            }
            else if(behavior==PersonAction.DONOTREPLY) {
                Log.info("Person "+name+ " will not reply");
                return;
            }
            else if(behavior==PersonAction.WAITYES) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SMS tosend2 = new SMS(phoneNb,msg.getFrom(),"yes");
                        Log.info("Person "+name+ " sending yes after "+ waitTime+ "ms");
                        sendSms.send(tosend2);
                        return;
                    }
                }, waitTime);


            }
            else if(behavior==PersonAction.WAITNO) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SMS tosend2 = new SMS(phoneNb,msg.getFrom(),"no");
                        Log.info("Person "+name+ " sending no after "+ waitTime+ "ms");
                        sendSms.send(tosend2);
                        return;
                    }
                }, waitTime);


            }

        }


    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



