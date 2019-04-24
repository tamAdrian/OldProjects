package proxy;

import model.ParticipantDTO;

import java.util.List;

public class FindByProbaDTO {
    private List<ParticipantDTO> list;

    public FindByProbaDTO(List<ParticipantDTO> list) {
        this.list = list;
    }

    public List<ParticipantDTO> getList() {
        return list;
    }

    public void setList(List<ParticipantDTO> list) {
        this.list = list;
    }
}
