package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonAction;
import lu.snt.serval.obligations.framework.PersonId;
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
        //System.out.println("ok");
        bootstrap("node0", "test.kevs");
        ContainerRoot cr= this.getCurrentModel("node0");

       // JSONModelLoader loader = new JSONModelLoader();
       // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

        TestCaseBuilder tcb= new TestCaseBuilder(cr);

        tcb.ChangeHCSSetting(15000,100000,10000);

        ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();

        PersonId p1 = new PersonId();
        p1.name= "Assaad";
        p1.behavior= PersonAction.SENDNO;
        p1.phoneNb= "123";
        t1a1.add(p1);

        PersonId p2 = new PersonId();
        p2.name= "Iram";
        p2.behavior= PersonAction.WAITYES;
        p2.waitReplyTime =2000;
        p2.waitDoorOpenTime = 20000;
        p2.phoneNb= "124";
        t1a1.add(p2);

        PersonId p3 = new PersonId();
        p3.name= "Francois";
        p3.behavior= PersonAction.WAITYES;
        p3.waitReplyTime =5000;
        p3.waitDoorOpenTime = 5000;
        p3.phoneNb= "125";
        t1a1.add(p3);

        tcb.addAddressBook(t1a1);

        this.deploy("node0",cr);


        
      waitLog("node0", "node0/* INFO: [HCS] Done", 1000000);
     /*   exec("node0", "set child1.started = \"false\"");
        assert (getCurrentModel("node0").findNodesByID("child1").getStarted() == false);
        waitLog("node0", "node0/* INFO: Stopping nodes[child1]", 5000);
        exec("node0", "set child1.started = \"true\"");
        assert (getCurrentModel("node0").findNodesByID("child1").getStarted() == true);
        waitLog("node0", "node0/child1/* INFO: Bootstrap completed", 10000);*/
    }
}
