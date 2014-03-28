package lu.snt.serval.obligations.api;

import lu.snt.serval.obligations.framework.PersonId;

import java.util.ArrayList;

/**
 * Created by iram.rubab on 3/28/2014.
 */
public class CallSmartPerson {

    //public ArrayList<PersonId> addressbook; //initially i wrote class SmartPerson instead of this, but changed it into test class and inout class
    private Integer personCount; //I do not see this in kevore?
    private long globalTime;
    private OStatus status;
    private long personTime;
    private SmartPerson p1;
    private boolean alarmOn;// this is equivalent to button press in kevore code


    public CallSmartPerson ()
    {
        status=OStatus.inactive;
    }

    public void Obligation()
    {
        if (alarmOn==true)
        {
            status=OStatus.active;
            searchHelper();
            helpRequest(p1);
            if(personTime<60000)
            {
                helpResponse(); //this is personReply in the kevore code
                status=OStatus.fulfilled;
            }
            else
            {
                status=OStatus.violated;
                if (globalTime< 180000 && personCount<3 )
                {
                    status=OStatus.active;
                }
                else
                {
                    callAmbulance(); //this is call emergency
                    status=OStatus.fulfilled;
                }
            }

        }
        else
        {
            status=OStatus.inactive;
        }


    }


    private void helpResponse() {
        // TODO Auto-generated method stub

    }

    public void helpRequest(SmartPerson person) {

    }


    public void callAmbulance() {

    }

    public SmartPerson searchHelper() {

        return null;

    }
    public enum OStatus {
        inactive,active,fulfilled,violated;

    }


}
