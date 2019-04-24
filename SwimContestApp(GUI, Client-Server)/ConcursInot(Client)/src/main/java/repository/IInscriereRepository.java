package repository;
import java.util.List;
import java.util.Map;

/*
define own methods
*/
public interface IInscriereRepository {

    public List<Integer> findByProbaId(Integer idProba);

    /*
        find all "proba" for a participant
    */
    public List<Integer> findByParticipantId(Integer idParticipant);

    /*
        group participants by each "proba"
     */
    public Map<Integer, Integer> groupByProbaId();
}
