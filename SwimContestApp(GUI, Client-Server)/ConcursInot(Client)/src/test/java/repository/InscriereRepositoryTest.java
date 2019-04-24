package repository;

import javafx.util.Pair;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class InscriereRepositoryTest {

    private InscriereRepository repository;
    private ParticipantRepository participantRepository;
    private ProbaRepository probaRepository;

    private Properties props = new Properties();
    private JdbcUtils dbUtils;


    @Before
    public void setUp() throws Exception {
        props.load(new FileInputStream("bdTests.config"));
        repository = new InscriereRepository(props);
        participantRepository = new ParticipantRepository(props);
        probaRepository = new ProbaRepository(props);

        participantRepository.save(new Participant(1,"Alex", 22));
        participantRepository.save(new Participant(2,"Mihai", 34));


        probaRepository.save(new Proba(1, Distanta.dist50m, Stil.liber));
        probaRepository.save(new Proba(2, Distanta.dist800m, Stil.mixt));

        repository.save(new Inscriere(1,1));
        repository.save(new Inscriere(1,2));
        repository.save(new Inscriere(2,2));
    }

    @After
    public void tearDown() throws Exception {
        repository.delete(new Pair<>(1,1));
        repository.delete(new Pair<>(1,2));
        repository.delete(new Pair<>(2,2));
    }

    @Test
    public void size() {
        assert(repository.size() == 3);
    }

    @Test
    public void save() {
        repository.save(new Inscriere(2,1));
        assert(repository.size() == 4);

        repository.delete(new Pair<>(2,1));
        assert(repository.size() == 3);

    }

    @Test
    public void delete() {
        repository.delete(new Pair<>(1,1));
        assert(repository.size() == 2);

    }

    @Test
    public void update() {
        repository.update(new Pair<>(1,1),new Inscriere(2,2));
        repository.delete(new Pair<>(2,2));
        assert(repository.size() == 2);
    }

    @Test
    public void findOne() {
        Inscriere inscriere = repository.findOne(new Pair<>(1,1));
        assert(inscriere.getID().getKey() == 1);
        assert(inscriere.getID().getValue() == 1);
    }

    @Test
    public void findAll() {
        List<Inscriere> inscriereList = new ArrayList<>();
        inscriereList = (ArrayList<Inscriere>) repository.findAll();
        assert(inscriereList.size() == 3);
    }

    @Test
    public void findByProbaId() {
        List<Inscriere> inscriereList =(ArrayList) repository.findByProbaId(2);
        assert(inscriereList.size() == 2);
    }
}