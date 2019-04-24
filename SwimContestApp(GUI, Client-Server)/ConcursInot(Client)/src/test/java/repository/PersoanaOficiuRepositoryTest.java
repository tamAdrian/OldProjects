package repository;

import model.PersoanaOficiu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.*;

public class PersoanaOficiuRepositoryTest {

    private JdbcUtils dbUtils;
    private PersoanaOficiuRepository repository;
    private Properties properties = new Properties();

    @Before
    public void setUp() throws Exception {
        properties.load(new FileInputStream("bdTests.config"));
        repository = new PersoanaOficiuRepository(properties);
        repository.save(new PersoanaOficiu("user1", "1463ca"));
        repository.save(new PersoanaOficiu("user2", "76323dsac"));
        repository.save(new PersoanaOficiu("user3", "14489cadsad"));
    }

    @After
    public void tearDown() throws Exception {
        repository.delete("user1");
        repository.delete("user2");
    }

    @Test
    public void size() {
        assert (repository.size() == 3);
    }

    @Test
    public void save() {
        repository.save(new PersoanaOficiu("user4", "wsasafa"));
        assert(repository.size() == 4);

        repository.delete("user4");
        assert(repository.size() == 3);
    }

    @Test
    public void delete() {
        repository.delete("user1");
        assert(repository.size() == 2);

        repository.delete("user2");
        assertEquals(repository.size(), 1);
    }

    @Test
    public void update() {
        repository.update("user1", new PersoanaOficiu("user577", "fsaf"));
        repository.delete("user577");

        assert(repository.size() == 2);
    }

    @Test
    public void findOne() {
        PersoanaOficiu persoanaOficiu = repository.findOne("user1");
        assert(persoanaOficiu.getID().equals("user1"));
    }

    @Test
    public void findAll() {
        Iterable<PersoanaOficiu> list = new ArrayList<>();
        list = (ArrayList<PersoanaOficiu>)repository.findAll();
        assert(((ArrayList<PersoanaOficiu>) list).size() == 3);
    }
}