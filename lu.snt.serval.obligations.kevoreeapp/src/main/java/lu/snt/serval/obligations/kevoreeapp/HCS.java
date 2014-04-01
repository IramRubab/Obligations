package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.CameraState;
import lu.snt.serval.obligations.framework.Context;
import lu.snt.serval.obligations.framework.PersonId;
import lu.snt.serval.obligations.framework.SMS;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@ComponentType
@Library(name = "Java")
public class HCS {

    @Param(defaultValue = "60000") //1min
    int personTimer=60000;
    @Param(defaultValue = "125000")
    int globalTimer=125000;
    @Param(defaultValue = "600000")
    int doorTimer=600000;
    @Param(defaultValue = "112")
    String emergencyNum="112";

    private String messageOK = "Thanks for your help, to open the door, use this code: ";
    private String message="Your neighbor Mme Annette needs help, check this video camera link, please reply yes if you can help in less than one minute.";
    private String emergencyMsg = "Annette becker needs help, please send an ambulance to this address: 2 rue de la gare, L0161, Luxembourg.";

   protected Context currentContex= Context.NORMAL;
    protected Timer globalTime;
    protected Timer nextPerson;

    protected Timer doorOpen;

    int currentPerson=0;
    boolean personReply=false;
    private PersonId currentPID;

    private int currentPassword;
    private Random rand = new Random();


    @Output
    org.kevoree.api.Port getContact;

    @Output
    org.kevoree.api.Port sendSms;

    @Output
    org.kevoree.api.Port cameraStatus;

    @Output
    org.kevoree.api.Port doorPassword;

    @Input
    public void buttonIn(Object i) {
        if(currentContex == Context.NORMAL)
        {
            currentContex=Context.EMERGENCY;
            startEmergency();
            Log.info("[HCS] Emergency started");
        }
        else
        {
            //To implement when many button press are detected
        }
    }


    private void setCurrentPassword()
    {
        currentPassword = rand.nextInt(899999)+100000;
        doorPassword.send(String.valueOf(currentPassword));
    }

    private void startEmergency()
    {
        setCurrentPassword();
        globalTime = new Timer();
        globalTime.schedule(new TimerTask() {
            @Override
            public void run() {
                if(currentContex==Context.EMERGENCY) {
                    Log.info("[HCS - GlobalTimer] timed out");
                    callEmergency();
                }
            }
        }, globalTimer);
        cameraStatus.send(CameraState.CONNECTED);
        askNextPerson();

    }

    private void askNextPerson()
    {
        personReply=false;
        getContact.send(currentPerson);
        currentPerson++;
    }

    @Input
    public void getSMS(Object i)
    {
        SMS msg= (SMS) i;
        if(msg.getFrom().equals(currentPID.phoneNb)){
            if(msg.getMessage().contains("yes")){
                personReply=true;
                if(nextPerson!=null){
                    nextPerson.cancel();
                }
                if(globalTime!=null){
                    globalTime.cancel();
                }

                SMS msgCode = new SMS("",currentPID.phoneNb,messageOK+String.valueOf(currentPassword));
                sendSms.send(msgCode);

                doorOpen = new Timer();
                doorOpen.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(currentContex==Context.EMERGENCY) {
                            Log.info("[HCS - doorTimer] Person " + currentPID.name + " didn't arrive, timed out, calling emergency");
                            callEmergency();
                        }
                    }
                }, doorTimer);

            }
            else
            {
                askNextPerson();
            }
        }

    }

    @Input
    public void doorOpen(Object i){
        Log.info("[HCS - door] Door open detected. ");
        reset();
    }

    public void reset(){
        Log.info("[HCS] Resetting, going back to normal context");
        currentContex=Context.NORMAL;
        setCurrentPassword();
        cameraStatus.send(CameraState.DISCONNECTED);
        Log.info("[HCS] Done");


        if(globalTime!=null)
        {
            globalTime.cancel();
            globalTime=null;
        }
        if(nextPerson!=null)
        {
            nextPerson.cancel();
            nextPerson=null;
        }
        if(doorOpen!=null)
        {
            doorOpen.cancel();
            doorOpen=null;
        }

    }

    @Input
    public void receiveContact(Object i) {
        try {
            currentPID= (PersonId) i;
            SMS msg = new SMS("",currentPID.phoneNb,message);
            sendSms.send(msg);
            personReply=false;
            nextPerson= new Timer();
            nextPerson.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(currentContex==Context.EMERGENCY && personReply==false) {
                        Log.info("[HCS - PersonTimer] Person " + currentPID.name + " didn't reply, timed out, calling next person");
                        askNextPerson();
                    }
                }
            }, personTimer);

        }
        catch (Exception ex)
        {
          //  ex.printStackTrace();
            Log.info("[HCS] Unable to get new contact, calling the emergency");
            currentPID = new PersonId();
            callEmergency();
        }

    }


    private void callEmergency(){
        if(currentContex== Context.EMERGENCY) {
            SMS emergencySMS = new SMS("", emergencyNum, emergencyMsg+" To open the door, please use this code: "+currentPassword);
            sendSms.send(emergencySMS);
            Log.info("[HCS] Message sent to emergency");
        }
    }




    @Start
    public void start() {

    }

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



