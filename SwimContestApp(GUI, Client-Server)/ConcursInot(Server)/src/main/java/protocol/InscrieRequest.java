package protocol;

import model.Distanta;
import model.ProbaDTO;
import model.Stil;

import java.util.ArrayList;
import java.util.List;

public class InscrieRequest implements Protocol {

    private Object dto;

    public InscrieRequest(Object dto) {
        this.dto = dto;
    }

    @Override
    public String objectToString() {
        InscrieDTO inscrieDTO = (InscrieDTO) this.dto;
        String string = RequestString.INSCRIE + "/";
        string += inscrieDTO.getNume() + "/" + inscrieDTO.getVarsta() + "/";
        int n = inscrieDTO.getList().size();
        for(int i = 0; i < n - 1; i++){
            ProbaDTO p = inscrieDTO.getList().get(i);
            string += p.getId() + "," + p.getDistanta() + "," + p.getStil() + "," + p.getNumarInscrisi() + "/";
        }
        ProbaDTO p = inscrieDTO.getList().get(n-1);
        string += p.getId() + "," + p.getDistanta() + "," + p.getStil() + "," + p.getNumarInscrisi();
        return string;
    }

    @Override
    public Object stringToObject() {
        String inscrieDTO = (String) this.dto;
        String[] p = inscrieDTO.split("/");
        String nume = p[1];
        int varsta = Integer.parseInt(p[2]);
        List<ProbaDTO> list = new ArrayList<>();

        for(int i = 3; i < p.length; i++){
            String[] parts = p[i].split(",");
            int id = Integer.parseInt(parts[0]);
            Distanta distanta = Distanta.valueOf(parts[1]);
            Stil stil = Stil.valueOf(parts[2]);
            int noInscrisi = Integer.valueOf(parts[3]);

            list.add(new ProbaDTO(id, distanta, stil, noInscrisi));
        }

        return new InscrieDTO(nume, varsta, list);
    }
}
