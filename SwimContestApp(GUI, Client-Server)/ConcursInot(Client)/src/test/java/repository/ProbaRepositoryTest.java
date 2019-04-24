package repository;

import model.Distanta;
import model.Participant;
import model.Proba;
import model.Stil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.*;

public class ProbaRepositoryTest {

    private ProbaRepository repository;
    private Properties props = new Properties();
    private JdbcUtils dbUtils;


    @Before
    public void setUp() throws Exception {
        props.load(new FileInputStream("bdTests.config"));
        repository = new ProbaRepository(props);

        repository.save(new Proba(1, Distanta.dist50m, Stil.liber));
        repository.save(new Proba(2, Distanta.dist800m, Stil.mixt));
        repository.save(new Proba(3, Distanta.dist1500m, Stil.fluture));
    }

    @After
    public void tearDown() throws Exception {
        repository.delete(1);
        repository.delete(2);
        repository.delete(3);
    }

    @Test
    public void size() {
        assert (repository.size() == 3);
    }

    @Test
    public void save() {
        repository.save(new Proba(4,Distanta.dist50m, Stil.mixt));
        assert(repository.size() == 4);

        repository.delete(4);
        assert(repository.size() == 3);
    }

    @Test
    public void delete() {
        repository.delete(1);
        assert(repository.size() == 2);

        repository.delete(2);
        assertEquals(repository.size(), 1);
    }

    @Test
    public void update() {
        repository.update(1, new Proba(57, Distanta.dist50m, Stil.mixt));
        repository.delete(57);

        assert(repository.size() == 2);
    }

    @Test
    public void findOne() {

        Proba proba = repository.findOne(1);
        assert(proba.getID() == 1);

    }

    @Test
    public void findAll() {
        Iterable<Proba> list = new ArrayList<>();
        list = (ArrayList<Proba>)repository.findAll();
        assert(((ArrayList<Proba>) list).size() == 3);
    }
}