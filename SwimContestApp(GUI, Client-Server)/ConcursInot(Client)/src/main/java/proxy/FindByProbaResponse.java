package proxy;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class FindByProbaResponse implements Protocol{

    private Object list;

    public FindByProbaResponse(Object list) {
        this.list = list;
    }

    @Override
    public String objectToString() {
        FindByProbaDTO dto = (FindByProbaDTO) list;
        String string = "";
        List<ParticipantDTO> list = dto.getList();
        for(ParticipantDTO p : list){
            string += p.getParticipant().getID() + "," +
                    p.getParticipant().getNume() + "," +
                    p.getParticipant().getVarsta() + ",";
            List<Proba> probe = p.getList();
            String probeString = "";
            for(Proba proba : probe){
                probeString += proba.getID() + ";" +
                        proba.getDistanta() + ";" +
                        proba.getStil() + "-";
            }
            probeString = probeString.substring(0, probeString.length()-1);
            string += probeString + "/";
        }
        string = string.substring(0, string.length()-1);
        return string;
    }

    @Override
    public Object stringToObject() {
        String string = (String) list;
        List<ParticipantDTO> list = new ArrayList<>();
        if(!string.equals("Nu exista participanti la aceasta proba")){
            String p[] = string.split("/");
            for(String part : p){
                String parts[] = part.split(",");
                int id = Integer.parseInt(parts[0]);
                String nume = parts[1];
                int varsta = Integer.parseInt(parts[2]);
                String probeParts[] = parts[3].split("-");
                List<Proba> listProba = new ArrayList<>();
                for(String probe : probeParts){
                    String probaParts[] = probe.split(";");
                    int idProba = Integer.parseInt(probaParts[0]);
                    Distanta distanta = Distanta.valueOf(probaParts[1]);
                    Stil stil = Stil.valueOf(probaParts[2]);
                    Proba proba = new Proba(idProba, distanta, stil);
                    listProba.add(proba);
                }
                Participant participant = new Participant(id, nume, varsta);
                ParticipantDTO participantDTO = new ParticipantDTO(participant, listProba);
                list.add(participantDTO);
            }
        }
        return new FindByProbaDTO(list);
    }
}
