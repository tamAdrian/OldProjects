package protocol;

public class LogoutRequest implements Protocol {


    @Override
    public String objectToString() {
        String string = "LOGOUT";
        return (String) string;
    }

    @Override
    public Object stringToObject() {
        String string = "LOGOUT";
        return  string;
    }
}
