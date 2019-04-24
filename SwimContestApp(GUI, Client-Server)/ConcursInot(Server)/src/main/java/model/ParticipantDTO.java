package model;

import java.util.List;

public class ParticipantDTO {
    private Participant participant;
    private List<Proba> list;

    public ParticipantDTO(Participant participant, List<Proba> list) {
        this.participant = participant;
        this.list = list;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Proba> getList() {
        return list;
    }

    public void setList(List<Proba> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final String[] probeString = {""};
        list.forEach(proba -> {
            probeString[0] += proba.getDistanta().toString() + " " + proba.getStil().toString() + ", ";
        });
        return "Nume : " + participant.getNume() + ", " +
                "Varsta : " + participant.getVarsta() + ", "+
                "Lista probe : " + list;
    }
}
