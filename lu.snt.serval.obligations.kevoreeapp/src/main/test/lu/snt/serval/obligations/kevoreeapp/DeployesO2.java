package lu.snt.serval.obligations.kevoreeapp;

import lu.snt.serval.obligations.framework.PersonAction;
import lu.snt.serval.obligations.framework.PersonId;
import org.junit.Test;
import org.kevoree.ContainerRoot;
import org.kevoree.loader.JSONModelLoader;
import org.kevoree.tools.test.KevoreeTestCase;

import java.util.ArrayList;
/**
 * Created by iram.rubab on 4/9/2014.
 */
public class DeployesO2 extends KevoreeTestCase {

    @Test //TestCase1 for Door Open Time Out  Equivalence Class Partitioning
    public void startupChildTest() throws Exception {
        //System.out.println("ok");
        bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
        ContainerRoot cr= this.getCurrentModel("node0");

        // JSONModelLoader loader = new JSONModelLoader();
        // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

        TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

        tcb.ChangeHCSSetting(20000,60000,40000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

        ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

        PersonId p1 = new PersonId();
        p1.name= "Assaad";
        p1.behavior= PersonAction.WAITYES;
        p1.waitReplyTime =15000;
        p1.waitDoorOpenTime = 20000;
        p1.retryTime=10;
        p1.phoneNb= "123";
        t1a1.add(p1);

        PersonId p2 = new PersonId();
        p2.name= "Iram";
        p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
        p2.waitReplyTime =15000;
        p2.waitDoorOpenTime = 40000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
        p2.retryTime=1000;
        p2.phoneNb= "124";
        t1a1.add(p2);

        PersonId p3 = new PersonId();
        p3.name= "Francois";
        p3.behavior= PersonAction.WAITYES;
        p3.waitReplyTime =15000;
        p3.waitDoorOpenTime = 40000;
        p3.retryTime=1000;
        p3.phoneNb= "125";
        t1a1.add(p3);

        tcb.addAddressBook(t1a1);  //Add address book to test case

        this.deploy("node0",cr);   //this mendatory to deploy Kevoree

      /*  @Test //TestCase2 for Door Open Time Out  Equivalence Class Partitioning
        public void startupChildTest() throws Exception {
            //System.out.println("ok");
            bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
            ContainerRoot cr= this.getCurrentModel("node0");

            // JSONModelLoader loader = new JSONModelLoader();
            // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

            TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

            tcb.ChangeHCSSetting(20000,60000,90000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

            ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

            PersonId p1 = new PersonId();
            p1.name= "Assaad";
            p1.behavior= PersonAction.WAITYES;
            p1.waitReplyTime =15000;
            p1.waitDoorOpenTime = -5000;
            p1.phoneNb= "123";
            t1a1.add(p1);

            PersonId p2 = new PersonId();
            p2.name= "Iram";
            p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
            p2.waitReplyTime =15000;
            p2.waitDoorOpenTime = -80000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
            p2.phoneNb= "124";
            t1a1.add(p2);

            PersonId p3 = new PersonId();
            p3.name= "Francois";
            p3.behavior= PersonAction.WAITYES;
            p3.waitReplyTime =15000;
            p3.waitDoorOpenTime = -80000;
            p3.phoneNb= "125";
            t1a1.add(p3);

            tcb.addAddressBook(t1a1);  //Add address book to test case

            this.deploy("node0",cr); */  //this mendatory to deploy Kevoree

       /* @Test //TestCase3 for Door Open Time Out  Equivalence Class Partitioning
        public void startupChildTest() throws Exception {
            //System.out.println("ok");
            bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
            ContainerRoot cr= this.getCurrentModel("node0");

            // JSONModelLoader loader = new JSONModelLoader();
            // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

            TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

            tcb.ChangeHCSSetting(20000,60000,90000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

            ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

            PersonId p1 = new PersonId();
            p1.name= "Assaad";
            p1.behavior= PersonAction.WAITYES;
            p1.waitReplyTime =15000;
            p1.waitDoorOpenTime = 70000;
            p1.phoneNb= "123";
            t1a1.add(p1);

            PersonId p2 = new PersonId();
            p2.name= "Iram";
            p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
            p2.waitReplyTime =15000;
            p2.waitDoorOpenTime = 80000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
            p2.phoneNb= "124";
            t1a1.add(p2);

            PersonId p3 = new PersonId();
            p3.name= "Francois";
            p3.behavior= PersonAction.WAITYES;
            p3.waitReplyTime =15000;
            p3.waitDoorOpenTime = 80000;
            p3.phoneNb= "125";
            t1a1.add(p3);

            tcb.addAddressBook(t1a1);  //Add address book to test case

            this.deploy("node0",cr); */  //this mendatory to deploy Kevoree

       /* @Test //TestCase4 for Door Open Time Out  Equivalence Class Partitioning
        public void startupChildTest() throws Exception {
            //System.out.println("ok");
            bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
            ContainerRoot cr= this.getCurrentModel("node0");

            // JSONModelLoader loader = new JSONModelLoader();
            // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

            TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

            tcb.ChangeHCSSetting(20000,60000,90000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

            ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

            PersonId p1 = new PersonId();
            p1.name= "Assaad";
            p1.behavior= PersonAction.WAITYES;
            p1.waitReplyTime =15000;
            p1.waitDoorOpenTime = 90000;
            p1.phoneNb= "123";
            t1a1.add(p1);

            PersonId p2 = new PersonId();
            p2.name= "Iram";
            p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
            p2.waitReplyTime =15000;
            p2.waitDoorOpenTime = 90000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
            p2.phoneNb= "124";
            t1a1.add(p2);

            PersonId p3 = new PersonId();
            p3.name= "Francois";
            p3.behavior= PersonAction.WAITYES;
            p3.waitReplyTime =15000;
            p3.waitDoorOpenTime = 90000;
            p3.phoneNb= "125";
            t1a1.add(p3);

            tcb.addAddressBook(t1a1);  //Add address book to test case

            this.deploy("node0",cr); */  //this mendatory to deploy Kevoree

      /* @Test //TestCase5 for Door Open Time Out  Equivalence Class Partitioning
        public void startupChildTest() throws Exception {
            //System.out.println("ok");
            bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
            ContainerRoot cr= this.getCurrentModel("node0");

            // JSONModelLoader loader = new JSONModelLoader();
            // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

            TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

            tcb.ChangeHCSSetting(20000,60000,90000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

            ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

            PersonId p1 = new PersonId();
            p1.name= "Assaad";
            p1.behavior= PersonAction.WAITYES;
            p1.waitReplyTime =15000;
            p1.waitDoorOpenTime = 92000;
            p1.phoneNb= "123";
            t1a1.add(p1);

            PersonId p2 = new PersonId();
            p2.name= "Iram";
            p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
            p2.waitReplyTime =15000;
            p2.waitDoorOpenTime = 920000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
            p2.phoneNb= "124";
            t1a1.add(p2);

            PersonId p3 = new PersonId();
            p3.name= "Francois";
            p3.behavior= PersonAction.WAITYES;
            p3.waitReplyTime =15000;
            p3.waitDoorOpenTime = 920000;
            p3.phoneNb= "125";
            t1a1.add(p3);

            tcb.addAddressBook(t1a1);  //Add address book to test case

            this.deploy("node0",cr); */  //this mendatory to deploy Kevoree


  /* @Test //TestCase1 for Door Open Time Out  Random Strategy
    public void startupChildTest() throws Exception {
        //System.out.println("ok");
        bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
        ContainerRoot cr= this.getCurrentModel("node0");

       // JSONModelLoader loader = new JSONModelLoader();
       // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

        TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

        tcb.ChangeHCSSetting(20000,60000,90000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

        ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

        PersonId p1 = new PersonId();
        p1.name= "Assaad";
        p1.behavior= PersonAction.WAITYES;
        p1.waitReplyTime =15000;
        p1.waitDoorOpenTime = 80000;
        p1.phoneNb= "123";
        t1a1.add(p1);

        PersonId p2 = new PersonId();
        p2.name= "Iram";
        p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
        p2.waitReplyTime =15000;
        p2.waitDoorOpenTime = 80000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
        p2.phoneNb= "124";
        t1a1.add(p2);

        PersonId p3 = new PersonId();
        p3.name= "Francois";
        p3.behavior= PersonAction.WAITYES;
        p3.waitReplyTime =15000;
        p3.waitDoorOpenTime = 80000;
        p3.phoneNb= "125";
        t1a1.add(p3);

        tcb.addAddressBook(t1a1);  //Add address book to test case

        this.deploy("node0",cr); */  //this mendatory to deploy Kevoree

      /* @Test //TestCase2 for or Door Open Time Out   Random Strategy
       public void startupChildTest() throws Exception {
           //System.out.println("ok");
           bootstrap("node0", "test.kevs");    // Bootstrap and start kevoree
           ContainerRoot cr= this.getCurrentModel("node0");

           // JSONModelLoader loader = new JSONModelLoader();
           // ContainerRoot cr= (ContainerRoot) loader.loadModelFromStream(Deployer.class.getClassLoader().getResourceAsStream("test.json")).get(0);

           TestCaseBuilder tcb= new TestCaseBuilder(cr); //Create a new test case

           tcb.ChangeHCSSetting(20000,60000,95000);   //for changing personTimer, globalTimer, doorTimer in HCS configuration

           ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();  // Create an address book with person behaviors

           PersonId p1 = new PersonId();
           p1.name= "Assaad";
           p1.behavior= PersonAction.WAITYES;
           p1.waitReplyTime =15000;
           p1.waitDoorOpenTime = 92000;
           p1.phoneNb= "123";
           t1a1.add(p1);

           PersonId p2 = new PersonId();
           p2.name= "Iram";
           p2.behavior= PersonAction.WAITYES;  //Whenever you choose waityes or waitno, you should provide the waitreplytime, otherwise it is not needed
           p2.waitReplyTime =15000;
           p2.waitDoorOpenTime = 80000;   //whenever you choose yes or waityes, you have to provide the time after which the person will arrive and try to open door
           p2.phoneNb= "124";
           t1a1.add(p2);

           PersonId p3 = new PersonId();
           p3.name= "Francois";
           p3.behavior= PersonAction.WAITYES;
           p3.waitReplyTime =15000;
           p3.waitDoorOpenTime = 80000;
           p3.phoneNb= "125";
           t1a1.add(p3);

           tcb.addAddressBook(t1a1);  //Add address book to test case

           this.deploy("node0",cr);*/   //this mendatory to deploy Kevoree

        waitLog("node0", "node0/* INFO: [DoorLoc] The password is incorrect", 1000000); //that the test case is completed successfully whenever the HCS prints done on the log
     /*   exec("node0", "set child1.started = \"false\"");
        assert (getCurrentModel("node0").findNodesByID("child1").getStarted() == false);
        waitLog("node0", "node0/* INFO: Stopping nodes[child1]", 5000);
        exec("node0", "set child1.started = \"true\"");
        assert (getCurrentModel("node0").findNodesByID("child1").getStarted() == true);
        waitLog("node0", "node0/child1/* INFO: Bootstrap completed", 10000);*/
    }
}
