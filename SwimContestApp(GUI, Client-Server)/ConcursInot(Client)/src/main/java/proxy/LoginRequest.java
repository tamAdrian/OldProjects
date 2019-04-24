package proxy;

import model.PersoanaOficiu;

public class LoginRequest implements Protocol {
    private Object oficiu;

    public LoginRequest(Object oficiu) {
        this.oficiu = oficiu;
    }

    @Override
    public String objectToString() {
        PersoanaOficiu of = (PersoanaOficiu) oficiu;
        return RequestString.LOGIN + "/" + of.getID() + "/" + of.getPassword();
    }

    @Override
    public PersoanaOficiu stringToObject() {
        String of = (String) oficiu;
        String p[] = of.split("/");
        return new PersoanaOficiu(p[1], p[2]);
    }
}
