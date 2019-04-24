package model;

import java.io.Serializable;

public class PersoanaOficiu implements HasID<String>, Serializable {

    private String id;
    private String password;

    public PersoanaOficiu(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersoanaOficiu{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
