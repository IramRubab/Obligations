package lu.snt.serval.obligations.kevoreeapp2;

import org.kevoree.annotation.*;

@ComponentType
@Library(name = "SmartHospital")
public class HCS {

    @KevoreeInject
    org.kevoree.api.Context context;

    @Output
    org.kevoree.api.Port out;

    @Input
    public void instrumentIn(Object i) {

    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



