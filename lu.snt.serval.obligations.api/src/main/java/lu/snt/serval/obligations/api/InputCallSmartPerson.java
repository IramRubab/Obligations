package lu.snt.serval.obligations.api;

import lu.snt.serval.obligations.framework.PersonId;

import java.util.ArrayList;

/**
 * Created by iram.rubab on 3/28/2014.
 */
public class InputCallSmartPerson {
    public int globalTime;
    public int personTime;
    public ArrayList<PersonId> addressbook; //this is SmartPerson as u see in CallSmartPerson
    public Boolean personReply;
}
