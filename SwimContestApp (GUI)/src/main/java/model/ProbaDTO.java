package model;

//probe si numar de participanti inscrisi la proba
public class ProbaDTO {

    private Integer id;
    private Distanta distanta;
    private Stil stil;
    private Integer numarInscrisi;

    public ProbaDTO(Integer id, Distanta distanta, Stil stil, Integer numarInscrisi) {
        this.id = id;
        this.distanta = distanta;
        this.stil = stil;
        this.numarInscrisi = numarInscrisi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getNumarInscrisi() {
        return numarInscrisi;
    }

    public void setNumarInscrisi(Integer numarInscrisi) {
        this.numarInscrisi = numarInscrisi;
    }

    @Override
    public String toString() {
        return "ProbaDTO{" +
                "id=" + id +
                ", distanta=" + distanta +
                ", stil=" + stil +
                ", numarInscrisi=" + numarInscrisi +
                '}';
    }
}
