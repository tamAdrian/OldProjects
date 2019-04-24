package protocol;

import model.ProbaDTO;

import java.util.List;

public class InscrieDTO {
    private String nume;
    private Integer varsta;
    private List<ProbaDTO> list;

    public InscrieDTO(String nume, Integer varsta, List<ProbaDTO> list) {
        this.nume = nume;
        this.varsta = varsta;
        this.list = list;
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

    public List<ProbaDTO> getList() {
        return list;
    }

    public void setList(List<ProbaDTO> list) {
        this.list = list;
    }
}
