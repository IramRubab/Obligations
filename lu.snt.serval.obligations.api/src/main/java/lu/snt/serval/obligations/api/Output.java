package lu.snt.serval.obligations.api;

public class Output {

    public boolean helpResponseFlag; //We will use this flag to check if SmartPerson has replied in time
    public boolean globalTimeFlag; //to check if the global time is over
    public boolean personTime; //to check if the person time is over, maybe its same as helpResponse flag
    public boolean PersonCountFlag; //to check if the number of person is reached to 3
    public boolean doorStatusFlag; //to check if door is Open or CLose
    public boolean cameraActivationFlag; //to see if camera is active or not

}