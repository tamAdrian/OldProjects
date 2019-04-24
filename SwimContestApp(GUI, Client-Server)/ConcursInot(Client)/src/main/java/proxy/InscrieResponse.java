package proxy;

public class InscrieResponse implements Protocol {

    private Object string;

    public InscrieResponse(Object string) {
        this.string = string;
    }

    @Override
    public String objectToString() {
        return (String) string;
    }

    @Override
    public Object stringToObject() {
        return string;
    }
}
