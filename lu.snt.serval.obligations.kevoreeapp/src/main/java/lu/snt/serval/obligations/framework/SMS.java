package lu.snt.serval.obligations.framework;

/**
 * Created by assaad.moawad on 3/24/2014.
 */
public class SMS {
    private String from;
    private String to;
    private String message;

    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public String getMessage(){
        return message;
    }

    public void setFrom(String from)
    {
        this.from=from;
    }

    public void setTo(String to)
    {
        this.to=to;
    }

    public SMS(String from, String to, String message)
    {
        this.from=from;
        this.to=to;
        this.message=message;
    }

}
