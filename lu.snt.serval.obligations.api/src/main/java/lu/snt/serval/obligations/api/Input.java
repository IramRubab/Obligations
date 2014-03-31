package lu.snt.serval.obligations.api;

import lu.snt.serval.obligations.framework.PersonId;

import java.util.ArrayList;

/**
 * Created by iram.rubab on 3/27/2014.
 */
public class Input {
    public int globalTime; // Time before the system calls emergency
    public int personTime; // Time for a person to reply, before system calls next person
    public int doorTime; //When a person replies yes, the time allowed to open the door before system calls emergency.

    //GlobalTime >= n*personTime + doorTime - this is a normal case

    // globalTime = 10 sec
    // personTime = 4sec
    // doorTime = 3 sec

    public ArrayList<PersonId> addressbook;

}
