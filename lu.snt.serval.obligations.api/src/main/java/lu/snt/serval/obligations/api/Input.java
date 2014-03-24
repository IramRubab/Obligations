package lu.snt.serval.obligations.api;

public class Input_CallSmartPerson{
    private ECL eCL;

    private long personCount;

    private long globalTime;

    private OStatus status; // OStatus is an enumeration comprising of inactive, active, fulfilled, and violated literals.
    private SmartPerson person; // a collection of persons which is contained in ECL along with the priorities
    private long singletime;

}

public class Input_DoorOpenTimeOut{


    private long time;
    private OStatus status; // OStatus is an enumeration comprising of inactive, active, fulfilled, and violated literals.

}


public class Input_AccessCodeUsage{


    private OStatus status; // OStatus is an enumeration comprising of inactive, active, fulfilled, and violated literals.
    private String Password;

}

public class Input_AccessCodeExpiry{


    private OStatus status; // OStatus is an enumeration comprising of inactive, active, fulfilled, and violated literals.
    private long time;

}

ublic class Input_CameraAccess{


    private OStatus status; // OStatus is an enumeration comprising of inactive, active, fulfilled, and violated literals.
    private long cameraShutDownTime;

}