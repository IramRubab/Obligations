package lu.snt.serval.obligations.kevoreeapp2;

import lu.snt.serval.obligations.framework.DoctorMsg;
import lu.snt.serval.obligations.framework.PersonAction;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@ComponentType
@Library(name = "SmartHospital")
public class Doctor {
    @Param(defaultValue = "0")
    public int behavior= PersonAction.SENDYES.ordinal();

    @Param(defaultValue = "1000")
    public int waitReplyTime =1000;

    private Random random=new Random();



    @KevoreeInject
    org.kevoree.api.Context context;

    @Output
    org.kevoree.api.Port doctorOut;

    @Input
    public void  doctorIn(Object i) {

        final DoctorMsg msg = (DoctorMsg) i;
        String tosend;
        final String name = context.getInstanceName();

        if(msg.doctorName.equals(name)){
            if (behavior == PersonAction.SENDYES.ordinal()) {
                tosend = name + " yes";
                Log.info("[" + name + "] Replying yes");
                doctorOut.send(tosend);
                return;
            } else if (behavior == PersonAction.SENDNO.ordinal()) {
                tosend = name + " no";
                Log.info("[" + name + "] Replying no");
                doctorOut.send(tosend);
                return;
            } else if (behavior == PersonAction.SENDRANDOM.ordinal()) {
                if (random.nextBoolean()) {
                    tosend = name + " yes";
                    Log.info("[" + name + "] Replying yes");
                    doctorOut.send(tosend);
                    return;
                } else {
                    tosend = name + " no";
                    Log.info("[" + name + "] Replying no");
                    doctorOut.send(tosend);
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
                        String tosend2 = name + " yes";
                        Log.info("[" + name + "] Replying yes after " + waitReplyTime + "ms");
                        doctorOut.send(tosend2);
                        return;
                    }
                }, waitReplyTime);


            } else if (behavior == PersonAction.WAITNO.ordinal()) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String tosend2 = name + " no";
                        Log.info("[" + name + "] Replying no after " + waitReplyTime + "ms");
                        doctorOut.send(tosend2);
                        return;
                    }
                }, waitReplyTime);
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



