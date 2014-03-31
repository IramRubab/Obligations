package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonId;
import lu.snt.serval.obligations.framework.SMS;
import org.kevoree.*;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.api.ModelService;
import org.kevoree.log.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ComponentType
@Library(name = "Java")
public class SMSCenter {

    @KevoreeInject
    ModelService modelService;

    @Param(defaultValue = "00352611223344")
    String phone="00352611223344";


    private ArrayList<ComponentInstance> getAddressBook(){

        ArrayList<ComponentInstance> res = new ArrayList<ComponentInstance>();
        ContainerNode containerNode= modelService.getCurrentModel().getModel().getNodes().get(0);
        for(ComponentInstance c: containerNode.getComponents())
        {
            if(c.getTypeDefinition().getName().toString().equals("SMSCenter")){
                for(Port p: c.getRequired()){
                    if(p.getName().equals("subscribers")){
                        //System.out.println("port found");
                        List<MBinding> bind =  p.getBindings().get(0).getHub().getBindings();
                        for(MBinding b: bind){
                            ComponentInstance ci= (ComponentInstance) b.getPort().eContainer();
                            if(ci.getTypeDefinition().getName().toString().equals("Person")&& ci.getName().equals("Emergency")==false){
                                res.add(ci);
                            }

                        }
                    }
                }
            }
        }
        return res;
    }

    private ComponentInstance getCompId(ArrayList<ComponentInstance> adr, int compId){
        for(ComponentInstance c: adr){
            if(c.getDictionary().findValuesByID("order").getValue().equals(String.valueOf(compId))){
                //System.out.println("[NO ERROR] found "+compId);
                return c;
            }

        }
       // System.out.println("[ERROR] couldn't find "+compId + " returning null");
        return null;
    }

    @Input
    public void getPerson(Object i){
        try {
            Integer num = (Integer) i;
            ArrayList<ComponentInstance> adr = getAddressBook();
            //Log.info("[SMSCenter] Address Book contains "+adr.size()+" elements");
            if(num>=adr.size()) {
               // Log.info("[SMSCenter] contact not found number"+num);
                contact.send("not found");
                return;
            }
            else {
                ComponentInstance ci= getCompId(adr,num);
                PersonId temp = new PersonId();
                temp.name=ci.getDictionary().findValuesByID("name").getValue();
                temp.phoneNb=ci.getDictionary().findValuesByID("phoneNb").getValue();
                Log.info("[SMSCenter] Sending Contact of "+temp.name+" phone: " + temp.phoneNb);
                contact.send(temp);
                return;

            }
        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
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
        Log.info("[SMSCenter] Message received from HCS, Forwarding to: "+msg.getTo());
        subscribers.send(msg);
    }

    @Input
    public void receiveSms(Object i) {
        SMS msg= (SMS) i;
        Log.info("[SMSCenter] Message received from: "+msg.getFrom()+ ", forwarding to HCS");
        smsReceivedHcs.send(i);
    }

    @Start
    public void start() {}

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}



