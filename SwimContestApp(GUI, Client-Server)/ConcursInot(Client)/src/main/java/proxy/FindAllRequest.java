package proxy;

public class FindAllRequest implements Protocol {

    @Override
    public String objectToString() {
        return RequestString.FINDALL.toString();
    }

    @Override
    public Object stringToObject() {
        return RequestString.FINDALL;
    }
}
