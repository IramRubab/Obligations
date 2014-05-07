package lu.snt.serval.obligations.framework;

import java.util.Date;

/**
 * Created by assaad.moawad on 06/05/2014.
 */
public class PulseMsg {
    public String name;
    public double measurement;
    public Date date;

    @Override
    public String toString(){
        return date.toString()+" ["+name+"]: "+measurement;
    }
}
