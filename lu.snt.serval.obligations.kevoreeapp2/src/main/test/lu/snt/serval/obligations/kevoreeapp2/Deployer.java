package lu.snt.serval.obligations.kevoreeapp2;

import lu.snt.serval.obligations.framework.InstrumentConfig;
import org.junit.Test;
import org.kevoree.ContainerRoot;
import org.kevoree.loader.JSONModelLoader;
import org.kevoree.tools.test.KevoreeTestCase;

import java.util.ArrayList;

/**
 * Created by assaad.moawad on 3/27/2014.
 */
public class Deployer extends KevoreeTestCase {

    @Test
    public void startupChildTest() throws Exception {

        bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
        ContainerRoot cr= this.getCurrentModel("node0");

        // JSONModelLoader loader = new JSONModelLoader();
        // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

        TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

        //Adding a first instrument to measure heart rate
        InstrumentConfig heartConf = new InstrumentConfig();
        heartConf.name="heart";
        heartConf.measurement=50;
        heartConf.interval=2000;
        tcb.addInstrument(heartConf);

        //Adding a second instrument to measure blood pressure
        InstrumentConfig bloodConf = new InstrumentConfig();
        bloodConf.name="blood";
        bloodConf.measurement=1.0;
        bloodConf.interval=2000;
        tcb.addInstrument(bloodConf);

        //Adding a third instrument to measure pulseOut oximeter
        InstrumentConfig pulseConf = new InstrumentConfig();
        pulseConf.name="pulse";
        pulseConf.measurement=12;
        pulseConf.interval=2000;
        tcb.addInstrument(pulseConf);


        this.deploy("node0",cr);  //this mendatory to deploy Kevoree
        waitLog("node0", "node0/* INFO: [HCS] Done", 1000000); //that the test case is completed successfully whenever the HCS prints done on the log




    }
}
