package repository;

import model.Participant;
import model.Proba;

import java.util.List;

public interface IParticipantRepository {
    public Participant findByNameAndAge(String nume, Integer varsta);
}
