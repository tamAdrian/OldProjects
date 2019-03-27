package model;

//AUTOINCREMENT

public class Participant implements HasID<Integer> {

    private Integer id = 0;
    private String nume;
    private Integer varsta;

    public Participant(Integer id, String nume, Integer varsta) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", varsta='" + varsta + '\'' +
                '}';
    }


}
