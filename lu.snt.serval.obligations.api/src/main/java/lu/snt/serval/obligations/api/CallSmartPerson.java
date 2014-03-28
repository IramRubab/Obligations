package lu.snt.serval.obligations.api;

/**
 * Created by iram.rubab on 3/28/2014.
 */
public class CallSmartPerson {

    public ArrayList<PersonId> addressbook;
    private Integer personCount;
    private long globalTime;
    private OStatus status;
    private SmartPerson person;
    private long personTime;
    private boolean alarmOn;


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
            helpRequest(person);
            if(personTime<60000)
            {
                helpResponse();
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
                    callAmbulance();
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
    public boolean alarmOn()
    {

        return false;
    }

    public SmartPerson searchHelper() {

        return null;

    }


}
