package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonId;
import lu.snt.serval.obligations.framework.SMS;
import org.kevoree.annotation.*;
import org.kevoree.log.Log;

import java.util.ArrayList;
import java.util.List;

@ComponentType
@Library(name = "Java")
public class SMSCenter {

    @Param(defaultValue = "00352611223344")
    String phone="00352611223344";

    private ArrayList<PersonId> addressBook = new ArrayList<PersonId>();


    public void AddPerson(PersonId p)
    {
        addressBook.add(p);
    }

    @Input
    public void getPerson(Object i){
        try {
            Integer num = (Integer) i;
            if (num<addressBook.size())
            {
                contact.send(addressBook.get(num));
                return;
            }
            else
            {
                contact.send("not found");
                return;
            }
        }
        catch (Exception ex)
        {
            contact.send("not found");
            return;
        }
    }



    @Output
    org.kevoree.api.Port subscribers;

    @Output
    org.kevoree.api.Port contact;

    @Output
    org.kevoree.api.Port smsReceivedHcs;

    @Input
    public void sendSms(Object i) {

        SMS msg= (SMS) i;
        msg.setFrom(phone);
        Log.info("[SMS center] Message received from HCS, Forwarding to: "+msg.getTo());
        subscribers.send(msg);
    }

    @Input
    public void receiveSms(Object i) {
        SMS msg= (SMS) i;
        Log.info("[SMS center] Message received from: "+msg.getFrom()+ ", forwarding to HCS");
        smsReceivedHcs.send(i);
    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



