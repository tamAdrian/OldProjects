package protocol;

import model.Distanta;
import model.ProbaDTO;
import model.Stil;

import java.util.ArrayList;
import java.util.List;

public class UpdateResponse implements Protocol {
    private Object list;

    public UpdateResponse(Object list) {
        this.list = list;
    }

    @Override
    public String objectToString() {
        FindAllDTO lista = (FindAllDTO) this.list;
        String string = "UPDATE/";
        int n = lista.getList().size();
        for(int i = 0; i < n; i++){
            ProbaDTO probaDTO = lista.getList().get(i);
            Integer id = probaDTO.getId();
            Distanta distanta = probaDTO.getDistanta();
            Stil stil = probaDTO.getStil();
            Integer nrInscrisi = probaDTO.getNumarInscrisi();

            string += id.toString() + "," + distanta.toString() + "," + stil.toString() + "," + nrInscrisi.toString() + "/";
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    @Override
    public Object stringToObject() {
        String list = (String) this.list;
        String[] parts = list.split("/");

        List<ProbaDTO> listDTO = new ArrayList<>();

        for(int i = 0; i < parts.length; i++){
            if(!parts.equals("UPDATE")){
                String[] p = parts[i].split(",");
                ProbaDTO probaDTO = new ProbaDTO( Integer.parseInt(p[0]),Distanta.valueOf(p[1]),Stil.valueOf(p[2]), Integer.parseInt(p[3]));
                listDTO.add(probaDTO);
            }
        }
        return new FindAllDTO(listDTO);
    }
}
