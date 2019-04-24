package proxy;

import model.ProbaDTO;

import java.util.List;

public class FindAllDTO {
    private List<ProbaDTO> list;

    public FindAllDTO(List<ProbaDTO> list) {
        this.list = list;
    }

    public List<ProbaDTO> getList() {
        return list;
    }

    public void setList(List<ProbaDTO> list) {
        this.list = list;
    }
}
