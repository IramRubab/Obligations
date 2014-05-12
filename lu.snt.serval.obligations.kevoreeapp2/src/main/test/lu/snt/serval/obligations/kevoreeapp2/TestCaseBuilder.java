package lu.snt.serval.obligations.kevoreeapp2;


import lu.snt.serval.obligations.framework.DoctorBehavior;
import lu.snt.serval.obligations.framework.InstrumentConfig;
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

    public void addHCSRule(String instrumentName, double min, double max, int interval){

    }

    private ComponentInstance findByType(String type){
        for(ComponentInstance c: containerRoot.getNodes().get(0).getComponents()){
            if(c.getTypeDefinition().getName().toString().equals(type)){
                return c;
            }
        }
        return null;
    }


    public void changeHospitalWaitTime(int interval){
        try
        {
            KevScriptEngine engine = new KevScriptEngine();
            String command = "set node0.hospital.waitReplyTime = \""+String.valueOf(interval)+"\"";
            engine.execute(command,containerRoot);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void changeDoctorBehavior(DoctorBehavior db){

        try
        {
        KevScriptEngine engine = new KevScriptEngine();
        String command = "set node0.personalDoc.waitReplyTime = \""+String.valueOf(db.waitReplyTime)+"\"";
        engine.execute(command,containerRoot);
            command = "set node0.personalDoc.behavior = \""+String.valueOf(db.behavior.ordinal())+"\"";
            engine.execute(command,containerRoot);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void addInstrument(InstrumentConfig ic){


        ComponentInstance hcs = findByType("HCS");
        ComponentInstance console= findByType("console");


        KevScriptEngine engine = new KevScriptEngine();
        try
        {
                String command = "add node0."+ic.name+" : Instrument";
                engine.execute(command,containerRoot);

                command = "set node0."+ic.name+".measurements = \""+String.valueOf(ic.measurement)+"\"";
                engine.execute(command,containerRoot);

                command = "set node0."+ic.name+".interval = \""+String.valueOf(ic.interval)+"\"";
                engine.execute(command,containerRoot);

                command = "bind node0."+ic.name+".pulseOut InstrumentIn";
                engine.execute(command,containerRoot);

                command = "bind node0."+ic.name+".pulseOut ConsoleIn";
                engine.execute(command,containerRoot);

                command = "bind node0."+ic.name+".command ConsoleOut";
                engine.execute(command,containerRoot);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }



    }

}
