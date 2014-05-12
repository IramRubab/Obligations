package lu.snt.serval.obligations.kevoreeapp2;

import lu.snt.serval.obligations.framework.PulseMsg;
import lu.snt.serval.obligations.framework.PulseObligation;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

import java.util.*;

@ComponentType
@Library(name = "SmartHospital")
public class HCS {

    @KevoreeInject
    org.kevoree.api.Context context;

    private HashMap<String,PulseObligation>  obligations = new HashMap<String,PulseObligation>();
    private HashMap<String,PulseMsg> latest=new HashMap<String, PulseMsg>();

    private Timer timerInstrumentCheck;

    @Param(defaultValue = "10000")
    int interval=10000;


    @Output
    org.kevoree.api.Port ConsoleOut;


    @Input
    public void ConsoleIn(Object i) {
        String s = (String) i;
        String [] commands = ((String) i).split(" ");
        try{
            if(commands[0].equals("hcs")|| commands[0].equals("all")){
                if (commands[1].trim().equals("start")) {
                    startTimer();
                }
                if(commands[1].trim().equals("stop")){
                    stopTimer();
                }
                if(commands[1].trim().equals("interval")){
                    interval = Integer.parseInt(commands[2].trim());
                    stopTimer();
                    startTimer();
                }
                if(commands[1].trim().equals("obligation")){
                    addObligation(commands[2].trim(),Double.parseDouble(commands[3].trim()),Double.parseDouble(commands[4].trim()));
                    ConsoleOut.send("[HCS] Obligation added");
                }
            }
        }
        catch(Exception ex){
           ConsoleOut.send("[HCS] Console command error "+ex.getMessage());
        }
    }


    private void startTimer(){
        timerInstrumentCheck = new Timer();
        timerInstrumentCheck.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkInstrument();
            }
        }, 0,interval);

    }

    private void stopTimer(){
        latest=new HashMap<String, PulseMsg>();
        if(timerInstrumentCheck!=null){
            timerInstrumentCheck.cancel();
        }
    }

    private void checkInstrument(){
        for(PulseObligation po: obligations.values()){
            try{
                PulseMsg msg=latest.get(po.name);
                if(msg==null)
                    return;
                Date now = new Date();
                if((now.getTime()-msg.date.getTime())>interval){
                    ConsoleOut.send("[HCS] Instrument "+po.name+" is not working regularly, HCS has detected a timeOut");
                    Log.info("[HCS] Instrument "+po.name+" is not working regularly, HCS has detected a timeOut");
                }

            }
            catch (Exception ex){
                ConsoleOut.send("[HCS] checking instrument "+po.name+" failed: "+ex.getMessage());
                Log.info("[HCS] checking instrument "+po.name+" failed: "+ex.getMessage());
            }
        }


    }

    @Input
    public void instrumentIn(Object i) {
        try{
            PulseMsg msg = (PulseMsg) i;
            checkObligation(msg);
            saveLatest(msg);
        }
        catch (Exception ex){
            Log.info("[HCS] Error inside Instrument In" + ex.getMessage());
        }
    }

    private void obligationViolation(PulseMsg msg, PulseObligation po){
        ConsoleOut.send("[HCS] EMERGENCY SITUATION DETECTED, "+msg.name+" measurement is "+msg.measurement+" tolerated levels are ["+po.min+" , "+po.max+"]! ");
        Log.info("[HCS] EMERGENCY SITUATION DETECTED, "+msg.name+" measurement is "+msg.measurement+" tolerated levels are ["+po.min+" , "+po.max+"]! ");

        //To implement call hospital
    }

    private void checkObligation(PulseMsg msg){
        if (obligations.containsKey(msg.name)){
            PulseObligation po = obligations.get(msg.name);
            if(msg.measurement<po.min|| msg.measurement>po.max){
                obligationViolation(msg, po);
            }
        }
    }

    private void saveLatest(PulseMsg msg) {
        if (latest.containsKey(msg.name)){
            latest.replace(msg.name,msg);
        }
        else
        {
            latest.put(msg.name,msg);
        }
    }

    public void addObligation(String name, double min, double max){
        PulseObligation po = new PulseObligation();
        po.name=name;
        po.min=min;
        po.max=max;
        if(obligations.containsKey(po.name)){
            obligations.replace(po.name, po);
        }
        obligations.put(po.name,po);
    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



