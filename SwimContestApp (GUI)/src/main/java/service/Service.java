package service;

import model.*;
import repository.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Service {

    private InscriereRepository inscriereRepository;
    private ParticipantRepository participantRepository;
    private PersoanaOficiuRepository persoanaOficiuRepository;
    private ProbaRepository probaRepository;

    public Service(InscriereRepository inscriereRepository, ParticipantRepository participantRepository, PersoanaOficiuRepository persoanaOficiuRepository, ProbaRepository probaRepository) {
        this.inscriereRepository = inscriereRepository;
        this.participantRepository = participantRepository;
        this.persoanaOficiuRepository = persoanaOficiuRepository;
        this.probaRepository = probaRepository;
    }

    public boolean login(String username, String password){
        return persoanaOficiuRepository.findByUsernameAndPassword(username, password);
    }

    public List<ProbaDTO> numarParticipantiProbe(){

        ArrayList<Proba> probe = (ArrayList) probaRepository.findAll();
        Map<Integer, Integer> map = inscriereRepository.groupByProbaId();

        List<ProbaDTO> probeDTO = new ArrayList<>();

        probe.forEach(proba ->{
            if(map.containsKey(proba.getID())){
                int numar = map.get(proba.getID());
                ProbaDTO probaDTO = new ProbaDTO(proba.getID(),proba.getDistanta(), proba.getStil(), numar);
                probeDTO.add(probaDTO);
            }
            else{
                probeDTO.add( new ProbaDTO(proba.getID(),proba.getDistanta(), proba.getStil(), 0));
            }
        });
        return probeDTO;
    }

    //###
    public void writeLastId(Integer numar){


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./src/main/java/service/participantLastId.txt"))) {
            bw.write(numar.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Integer generateIdForParticipant(){

        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/service/participantLastId.txt"))) {
            String linie;
            linie = br.readLine();
            writeLastId(Integer.valueOf(linie) + 1);
            return Integer.valueOf(linie);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //###

    public Integer adaugaParticpant(String nume, Integer varsta){

        Participant participant = new Participant(generateIdForParticipant(), nume, varsta);
        participantRepository.save(participant);
        return participant.getID();

    }

    //inscrie un participant la mai multe probe
    public void inscrieParticipant(String nume, Integer varsta, List<ProbaDTO> probe){
        Integer id = adaugaParticpant(nume, varsta);
        probe.forEach(proba ->
            inscriereRepository.save(new Inscriere(id, proba.getId()))
        );
    }

    //toti participantii unei probe si toate probele la care participa
    public List<ParticipantDTO> cautaParticipantiProbe(Integer idProba){

        List<Integer> participanti = inscriereRepository.findByProbaId(idProba); //lista cu toti participantii
        List<ParticipantDTO> participantiDTOList = new ArrayList<>();
        participanti.forEach(participant ->{
            Participant p = participantRepository.findOne(participant);
            List<Integer> probe = inscriereRepository.findByParticipantId(p.getID());
            List<Proba> toateProbele = new ArrayList<>();
            probe.forEach(pr -> {
                toateProbele.add(probaRepository.findOne(pr));
            });
            participantiDTOList.add(new ParticipantDTO(p, toateProbele));
        });

        return participantiDTOList;
    }


}
