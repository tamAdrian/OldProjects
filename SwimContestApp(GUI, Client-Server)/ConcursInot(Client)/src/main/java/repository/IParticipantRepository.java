package repository;
import model.Participant;

/*
define own methods
*/
public interface IParticipantRepository {
    public Participant findByNameAndAge(String nume, Integer varsta);
}
