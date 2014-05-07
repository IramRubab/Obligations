package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonId;
import org.kevoree.*;
import org.kevoree.kevscript.KevScriptEngine;

import java.util.ArrayList;

/**
 * Created by assaad.moawad on 3/31/2014.
 */
public class TestCaseBuilder {
    private ContainerRoot containerRoot;
    public TestCaseBuilder(ContainerRoot container){
        this.containerRoot=container;
    }

    public void ChangeHCSSetting(int personTimer, int globalTimer, int doorTimer){
        ContainerNode node = containerRoot.getNodes().get(0);
        for(ComponentInstance c: node.getComponents())
        {
            if(c.getTypeDefinition().getName().toString().equals("HCS"))
            {
                c.getDictionary().findValuesByID("personTimer").setValue(String.valueOf(personTimer));
                c.getDictionary().findValuesByID("globalTimer").setValue(String.valueOf(globalTimer));
                c.getDictionary().findValuesByID("doorTimer").setValue(String.valueOf(doorTimer));
                break;
            }
        }
    }


    private ComponentInstance findByType(String type){
        for(ComponentInstance c: containerRoot.getNodes().get(0).getComponents()){
            if(c.getTypeDefinition().getName().toString().equals(type)){
                return c;
            }
        }
        return null;
    }

    public void addAddressBook(ArrayList<PersonId> address){
        ComponentInstance smscenter = findByType("SMSCenter");
        ComponentInstance dLock= findByType("DoorLock");
        String subscriber="";
        String smsreceived="";
        String openDoor="";

        for(Port p: smscenter.getRequired()) {
            if (p.getName().equals("subscribers")) {
                subscriber = p.getBindings().get(0).getHub().getName().toString();
            }
        }

        for(Port p: smscenter.getProvided()) {
            if (p.getName().equals("receiveSms")) {
                smsreceived = p.getBindings().get(0).getHub().getName().toString();
            }
        }

        for(Port p: dLock.getProvided()) {
            if (p.getName().equals("openDoor")) {
                openDoor = p.getBindings().get(0).getHub().getName().toString();
            }
        }

        KevScriptEngine engine = new KevScriptEngine();
        try
        {
            int counter=0;
            for(PersonId pid: address){
                String command = "add node0."+pid.name+" : Person";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".name = \""+pid.name+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".behavior = \""+String.valueOf(pid.behavior.ordinal())+"\"";
              //  System.out.println(pid.name+" : "+pid.behavior.ordinal());
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".waitReplyTime = \""+String.valueOf(pid.waitReplyTime)+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".retryTime = \""+String.valueOf(pid.retryTime)+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".phoneNb = \""+String.valueOf(pid.phoneNb)+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".waitDoorOpenTime = \""+String.valueOf(pid.waitDoorOpenTime)+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".order = \""+String.valueOf(counter)+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+pid.name+".started = \"true\"";
                engine.execute(command,containerRoot);

                command = "bind node0."+pid.name+".openDoor "+ openDoor;
                engine.execute(command,containerRoot);

                command = "bind node0."+pid.name+".receiveSMS "+ subscriber;
                engine.execute(command,containerRoot);

                command = "bind node0."+pid.name+".sendSms "+ smsreceived;
                engine.execute(command,containerRoot);
                counter++;
            }


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }







    }

}
