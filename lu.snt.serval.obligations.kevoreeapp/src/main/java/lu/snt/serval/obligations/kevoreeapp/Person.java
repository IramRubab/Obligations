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

    public PersonId personId= new PersonId();
    private static Random random = new Random();



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
            Log.info("[Person] " + personId.name + " received this sms: " + msg.getMessage());

            SMS tosend;
            if (((SMS) i).getTo().equals(personId.phoneNb)) {
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
                                    Log.info("Person " + personId.name + " trying to open the door after " + personId.waitDoorOpenTime + "ms, with password: "+code);
                                    openDoor.send(code);
                                    return;
                                }
                            }, personId.waitReplyTime);
                            break;

                        }
                    }
                }

                else if (msg.getMessage().contains("needs help")) {

                    if (personId.behavior == PersonAction.SENDYES) {
                        tosend = new SMS(personId.phoneNb, msg.getFrom(), "yes");
                        Log.info("Person " + personId.name + " sending yes");
                        sendSms.send(tosend);
                        return;
                    } else if (personId.behavior == PersonAction.SENDNO) {
                        tosend = new SMS(personId.phoneNb, msg.getFrom(), "no");
                        Log.info("Person " + personId.name + " sending no");
                        sendSms.send(tosend);
                        return;
                    } else if (personId.behavior == PersonAction.SENDRANDOM) {
                        if (random.nextBoolean()) {
                            tosend = new SMS(personId.phoneNb, msg.getFrom(), "yes");
                            Log.info("Person " + personId.name + " sending yes");
                            sendSms.send(tosend);
                            return;
                        } else {
                            tosend = new SMS(personId.phoneNb, msg.getFrom(), "no");
                            Log.info("Person " + personId.name + " sending no");
                            sendSms.send(tosend);
                            return;
                        }
                    } else if (personId.behavior == PersonAction.DONOTREPLY) {
                        Log.info("Person " + personId.name + " will not reply");
                        return;
                    } else if (personId.behavior == PersonAction.WAITYES) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SMS tosend2 = new SMS(personId.phoneNb, msg.getFrom(), "yes");
                                Log.info("Person " + personId.name + " sending yes after " + personId.waitReplyTime + "ms");
                                sendSms.send(tosend2);
                                return;
                            }
                        }, personId.waitReplyTime);


                    } else if (personId.behavior == PersonAction.WAITNO) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SMS tosend2 = new SMS(personId.phoneNb, msg.getFrom(), "no");
                                Log.info("Person " + personId.name + " sending no after " + personId.waitReplyTime + "ms");
                                sendSms.send(tosend2);
                                return;
                            }
                        }, personId.waitReplyTime);


                    }
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }


    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



