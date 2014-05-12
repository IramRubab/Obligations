package lu.snt.serval.obligations.kevoreeapp2;

import lu.snt.serval.obligations.framework.DoctorMsg;
import lu.snt.serval.obligations.framework.PulseMsg;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@ComponentType
@Library(name = "SmartHospital")
public class Hospital {



    private Boolean emergency=false;
    private Boolean doctorYes=false;
    private Timer timer=new Timer();


    @Param(defaultValue = "1000")
    public int waitReplyTime =15000;

    @KevoreeInject
    org.kevoree.api.Context context;

    @Output
    org.kevoree.api.Port doctorOut;

    @Output
    org.kevoree.api.Port hcsOut;


    @Input
    public void hcsIn(Object i) {
        PulseMsg msg =(PulseMsg)i;
        if(emergency==false)
        {
            doctorYes=false;
            emergency=true;
            Log.info("[Hospital] EMERGENCY SITUATION received");
            hcsOut.send("[Hospital] EMERGENCY SITUATION received");
            DoctorMsg msg2= new DoctorMsg();
            msg2.message=msg;
            msg2.doctorName= "personalDoc";
            doctorOut.send(msg2);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.info("[Hospital] Personal Doctor timed out, contacting the duty doctor");
                    hcsOut.send("Personal Doctor timed out, contacting the duty doctor");
                    DoctorMsg msg2= new DoctorMsg();
                    msg2.doctorName= "dutyDoc";
                    doctorOut.send(msg2);
                }
            }, waitReplyTime);
        }

    }





    @Input
    public void doctorIn(Object i) {
        String msg = (String) i;
        String [] sd= msg.split(" ");
        if(timer!=null)
            timer.cancel();

        if(doctorYes==false) {
            if (msg.contains("yes")) {
                doctorYes=true;
                String msgresult = "The " + sd[0] + " is checking the emergency situation, he is coming";
                Log.info("[Hospital] " + msgresult);
                hcsOut.send(msgresult);
            } else {
                Log.info("[Hospital] Personal doctor can't attend, forwarding to the duty doctor");
                hcsOut.send("Personal doctor can't attend, forwarding to the duty doctor");
                DoctorMsg msg2 = new DoctorMsg();
                msg2.doctorName = "dutyDoc";
                doctorOut.send(msg2);
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



