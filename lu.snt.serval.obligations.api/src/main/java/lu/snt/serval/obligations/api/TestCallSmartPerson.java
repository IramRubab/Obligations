package lu.snt.serval.obligations.api;

import lu.snt.serval.obligations.framework.PersonAction;
import lu.snt.serval.obligations.framework.PersonId;

import java.util.ArrayList;

/**
 * Created by iram.rubab on 3/28/2014.
 */
public class TestCallSmartPerson {

    public static void main(String[] args){
        // Test case 1
        Input test1 = new Input();
        test1.globalTime=0000;
        test1.personTime=30000;

        ArrayList<PersonId> t1a1 = new ArrayList<PersonId>();

        PersonId p1 = new PersonId();
        p1.name= "Assaad";
        p1.behavior= PersonAction.SENDNO;
        p1.phoneNb= "123";
        t1a1.add(p1);

        PersonId p2 = new PersonId();
        p2.name= "Iram";
        p2.behavior= PersonAction.WAITYES;
        p2.waitTime=90000; // means that Iram will wait 90 seconds before she replies yes
        p2.phoneNb= "124";
        t1a1.add(p2);

        PersonId p3 = new PersonId();
        p3.name= "Francois";
        p3.behavior= PersonAction.WAITYES;
        p3.waitTime=90000; // means that Iram will wait 90 seconds before she replies yes
        p3.phoneNb= "125";
        t1a1.add(p3);
        assertTrue(test1.personReply == true);

        Input test2 = new Input();
        test2.globalTime= 120000;
        test2.personTime= 90000;
        // how to maintain person count?
        assertTrue(test1.personReply == false);

        Input test3 = new Input();
        test3.globalTime= 240000;
        test3.personTime =90000;
        //flag on emergency call
}
