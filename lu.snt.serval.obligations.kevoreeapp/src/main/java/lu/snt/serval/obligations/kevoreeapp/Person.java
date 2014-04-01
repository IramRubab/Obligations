package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonAction;
import lu.snt.serval.obligations.framework.PersonId;
import lu.snt.serval.obligations.framework.SMS;
import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.annotation.*;
import org.kevoree.api.ModelService;
import org.kevoree.log.Log;
import java.util.Timer;


import java.util.Random;
import java.util.TimerTask;

@ComponentType
@Library(name = "Java")
public class Person {

    //public PersonId personId= new PersonId();
    private static Random random = new Random();

    @Param(defaultValue = "Emergency")
    public String name ="Emergency";

    @Param(defaultValue = "0")
    public int behavior= 0;

    @Param(defaultValue = "1000")
    public int waitReplyTime =1000;

    @Param(defaultValue = "1000")
    public int waitDoorOpenTime=1000;

    @Param(defaultValue = "112")
    public String phoneNb="112";

    @Param(defaultValue = "-1")
    public int order=-1;



    @KevoreeInject
    ModelService ms;

    @Output
    org.kevoree.api.Port sendSms;

    @Output
    org.kevoree.api.Port openDoor;

    @Input
    public void receiveSMS(Object i) {

        try {
            final SMS msg = (SMS) i;
            if (((SMS) i).getTo().equals(phoneNb)) {
                Log.info("[" + name + "] received this sms: " + msg.getMessage());
                SMS tosend;

                if (msg.getMessage().contains("code:")) {
                    String splmsg[] = msg.getMessage().split("code:");
                    final String code = splmsg[1].trim();
                    ContainerRoot container = ms.getCurrentModel().getModel();
                    ContainerNode node = container.getNodes().get(0);
                    for (ComponentInstance c : node.getComponents()) {
                        if (c.getTypeDefinition().getName().toString().equals("DoorLock")) {
                           Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Log.info("[" + name + "] trying to open the door after " + waitDoorOpenTime + "ms, with password: "+code);
                                    openDoor.send(code);
                                    return;
                                }
                            }, waitDoorOpenTime);
                            break;

                        }
                    }
                }

                else if (msg.getMessage().contains("needs help")) {

                    if (behavior == PersonAction.SENDYES.ordinal()) {
                        tosend = new SMS(phoneNb, msg.getFrom(), "yes");
                        Log.info("[" + name + "] Replying yes");
                        sendSms.send(tosend);
                        return;
                    } else if (behavior == PersonAction.SENDNO.ordinal()) {
                        tosend = new SMS(phoneNb, msg.getFrom(), "no");
                        Log.info("[" + name + "] Replying no");
                        sendSms.send(tosend);
                        return;
                    } else if (behavior == PersonAction.SENDRANDOM.ordinal()) {
                        if (random.nextBoolean()) {
                            tosend = new SMS(phoneNb, msg.getFrom(), "yes");
                            Log.info("[" + name + "] Replying yes");
                            sendSms.send(tosend);
                            return;
                        } else {
                            tosend = new SMS(phoneNb, msg.getFrom(), "no");
                            Log.info("[" + name + "] Replying no");
                            sendSms.send(tosend);
                            return;
                        }
                    } else if (behavior == PersonAction.DONOTREPLY.ordinal()) {
                        Log.info("[" + name + "] will not reply");
                        return;
                    } else if (behavior == PersonAction.WAITYES.ordinal()) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SMS tosend2 = new SMS(phoneNb, msg.getFrom(), "yes");
                                Log.info("[" + name + "] Replying yes after " + waitReplyTime + "ms");
                                sendSms.send(tosend2);
                                return;
                            }
                        }, waitReplyTime);


                    } else if (behavior == PersonAction.WAITNO.ordinal()) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SMS tosend2 = new SMS(phoneNb, msg.getFrom(), "no");
                                Log.info("[" + name + "] Replying no after " + waitReplyTime + "ms");
                                sendSms.send(tosend2);
                                return;
                            }
                        }, waitReplyTime);


                    }
                }
            }
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
        }


    }


    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



