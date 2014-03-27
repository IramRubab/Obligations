package lu.snt.serval.obligations.kevoreeapp;

import org.junit.Test;
import org.kevoree.tools.test.KevoreeTestCase;

/**
 * Created by assaad.moawad on 3/27/2014.
 */
public class Deployer extends KevoreeTestCase {

    @Test
    public void startupChildTest() throws Exception {
        //System.out.println("ok");
        bootstrap("node0", "init.kevs");

        
      /*  waitLog("node0", "node0/child1/* INFO: Bootstrap completed", 10000);
        exec("node0", "set child1.started = \"false\"");
        assert (getCurrentModel("node0").findNodesByID("child1").getStarted() == false);
        waitLog("node0", "node0/* INFO: Stopping nodes[child1]", 5000);
        exec("node0", "set child1.started = \"true\"");
        assert (getCurrentModel("node0").findNodesByID("child1").getStarted() == true);
        waitLog("node0", "node0/child1/* INFO: Bootstrap completed", 10000);*/
    }
}
