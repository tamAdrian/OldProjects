package proxy;

public class LoginResponse implements Protocol {

    private Object validate;

    public LoginResponse(Object validate) {
        this.validate = validate;
    }

    @Override
    public String objectToString() {
        return validate.toString();
    }

    @Override
    public Boolean stringToObject() {
        return Boolean.valueOf((String)validate);
    }
}
