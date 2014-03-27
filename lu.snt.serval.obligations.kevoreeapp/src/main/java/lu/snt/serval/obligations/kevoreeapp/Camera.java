package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.CameraState;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

@ComponentType
@Library(name = "Java")
public class Camera {

    CameraState cs = CameraState.DISCONNECTED;

    @KevoreeInject
    org.kevoree.api.Context context;


    @Input
    public void changeStatus(Object i) {
        cs = (CameraState) i;
        Log.info("[Camera] Status changed "+ i.toString());
    }

    public String getCameraState()
    {
        String s="";
        if(cs==CameraState.CONNECTED)
            s="Camera is connected, streaming live video here";
        else
            s="Camera is disconnected can't access the stream";
        return s;
    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



