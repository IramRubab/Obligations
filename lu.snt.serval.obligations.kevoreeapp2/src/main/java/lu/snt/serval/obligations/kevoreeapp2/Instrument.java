package lu.snt.serval.obligations.kevoreeapp2;

import lu.snt.serval.obligations.framework.PulseMsg;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@ComponentType
@Library(name = "SmartHospital")
public class Instrument {


    @Param(defaultValue = "0.0")
    Double measurements;

    @Param(defaultValue = "1000")
    int interval=1000;

    @KevoreeInject
    org.kevoree.api.Context context;

    protected Timer pulseTimer;



    @Output
    org.kevoree.api.Port pulseOut;


    private void startTimer(){
        pulseTimer = new Timer();
        pulseTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                PulseMsg msg=new PulseMsg();
                msg.date=new Date();
                msg.name=context.getInstanceName();
                msg.measurement= measurements;
                pulseOut.send(msg);
            }
        }, 0,interval);
    }

    private void stopTimer(){
        if(pulseTimer!=null){
            pulseTimer.cancel();
        }
    }
    @Input
    public void command(Object i) {
        String s = (String) i;

        String [] commands = ((String) i).split(" ");
        try {

            if (commands[0].equals(context.getInstanceName())) {
                if (commands[1].trim().equals("start")) {
                    startTimer();
                }
                if(commands[1].trim().equals("stop")){
                    stopTimer();
                }
                if(commands[1].trim().equals("measure")){
                    measurements = Double.parseDouble(commands[2].trim());
                }
                if(commands[1].trim().equals("interval")){
                    interval = Integer.parseInt(commands[2].trim());
                    stopTimer();
                    startTimer();
                }

            }
        }
        catch (Exception ex){
            Log.info("["+context.getInstanceName()+"] Command error "+ ex.getMessage());
        }

    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



