package model;

public class Proba implements HasID<Integer>{

    private Integer id;
    private Distanta distanta;
    private Stil stil;

    public Proba(Integer id, Distanta distanta, Stil stil) {
        this.id = id;
        this.distanta = distanta;
        this.stil = stil;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    public Distanta getDistanta() {
        return distanta;
    }

    public void setDistanta(Distanta distanta) {
        this.distanta = distanta;
    }

    public Stil getStil() {
        return stil;
    }

    public void setStil(Stil stil) {
        this.stil = stil;
    }

    @Override
    public String toString() {
        return "Proba{" +
                "id=" + id +
                ", distanta=" + distanta +
                ", stil=" + stil +
                '}';
    }

}
