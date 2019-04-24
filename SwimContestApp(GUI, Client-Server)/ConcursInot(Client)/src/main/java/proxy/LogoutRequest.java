package proxy;

public class LogoutRequest implements Protocol {

    @Override
    public String objectToString() {
        String string = RequestString.LOGOUT.toString();
        return (String) string;
    }

    @Override
    public Object stringToObject() {
        String string = RequestString.LOGOUT.toString();
        return  string;
    }
}
