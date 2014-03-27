package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonAction;
import lu.snt.serval.obligations.framework.PersonId;
import lu.snt.serval.obligations.framework.SMS;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;
import java.util.Timer;


import java.util.Random;
import java.util.TimerTask;

@ComponentType
@Library(name = "Java")
public class Person {

    public PersonId current;
    private static Random random = new Random();


    @Output
    org.kevoree.api.Port sendSms;

    @Input
    public void receiveSMS(Object i) {
        final SMS msg=(SMS) i;
        SMS tosend;
        if(((SMS) i).getTo().equals(current.phoneNb))
        {
            if(current.behavior==PersonAction.SENDYES){
                tosend = new SMS(current.phoneNb,msg.getFrom(),"yes");
                Log.info("Person "+current.name+ " sending yes");
                sendSms.send(tosend);
                return;
            }
            else if(current.behavior==PersonAction.SENDNO){
                tosend = new SMS(current.phoneNb,msg.getFrom(),"no");
                Log.info("Person "+current.name+ " sending no");
                sendSms.send(tosend);
                return;
            }
            else if(current.behavior==PersonAction.SENDRANDOM){
                if(random.nextBoolean())
                {
                    tosend = new SMS(current.phoneNb,msg.getFrom(),"yes");
                    Log.info("Person "+current.name+ " sending yes");
                    sendSms.send(tosend);
                    return;
                }
                else
                {
                    tosend = new SMS(current.phoneNb,msg.getFrom(),"no");
                    Log.info("Person "+current.name+ " sending no");
                    sendSms.send(tosend);
                    return;
                }
            }
            else if(current.behavior==PersonAction.DONOTREPLY) {
                Log.info("Person "+current.name+ " will not reply");
                return;
            }
            else if(current.behavior==PersonAction.WAITYES) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SMS tosend2 = new SMS(current.phoneNb,msg.getFrom(),"yes");
                        Log.info("Person "+current.name+ " sending yes after "+ current.waitTime+ "ms");
                        sendSms.send(tosend2);
                        return;
                    }
                }, current.waitTime);


            }
            else if(current.behavior==PersonAction.WAITNO) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SMS tosend2 = new SMS(current.phoneNb,msg.getFrom(),"no");
                        Log.info("Person "+current.name+ " sending no after "+ current.waitTime+ "ms");
                        sendSms.send(tosend2);
                        return;
                    }
                }, current.waitTime);


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



