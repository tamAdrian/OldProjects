package repository;

import model.Inscriere;
import model.Proba;

import java.util.List;
import java.util.Map;

public interface IInscriereRepository {
    public List<Integer> findByProbaId(Integer idProba);
    public List<Integer> findByParticipantId(Integer idParticipant);
    public Map<Integer, Integer> groupByProbaId();
}
