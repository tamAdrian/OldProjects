package repository;

import model.Participant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ParticipantRepositoryTest {

    private ParticipantRepository repository;
    private Properties props = new Properties();
    private JdbcUtils dbUtils;

    @Before
    public void setUp() throws Exception {
            props.load(new FileInputStream("bdTests.config"));
            repository = new ParticipantRepository(props);
            repository.save(new Participant(1,"Alex", 22));
            repository.save(new Participant(2,"Mihai", 34));
    }

    @After
    public void tearDown() throws Exception {
        repository.delete(1);
        repository.delete(2);
    }

    @Test
    public void size() {
        assert (repository.size() == 2);
    }

    @Test
    public void save() {
        repository.save(new Participant(3,"Mihai", 43));
        assert (repository.size() == 3);

        repository.delete(3);
    }

    @Test
    public void delete() {
        repository.delete(1);
        assert(repository.size() == 1);

        repository.delete(2);
        assertEquals(repository.size(), 0);
    }

    @Test
    public void update() {
        repository.update(1,new Participant(50, "Dragos", 19));
        assertEquals(repository.size(), 2);

        repository.delete(50);
    }

    @Test
    public void findOne() {
        Participant participant = repository.findOne(1);
        assert(participant.getNume().equals("Alex"));
    }

    @Test
    public void findAll() {
        Iterable<Participant> list = new ArrayList<>();
        list = (ArrayList<Participant>)repository.findAll();
        assert(((ArrayList<Participant>) list).size() == 2);
    }
}