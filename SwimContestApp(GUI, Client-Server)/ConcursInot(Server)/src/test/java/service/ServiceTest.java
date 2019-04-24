package service;

import model.PersoanaOficiu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.InscriereRepository;
import repository.ParticipantRepository;
import repository.PersoanaOficiuRepository;
import repository.ProbaRepository;

import java.io.FileInputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class ServiceTest {

    private InscriereRepository repository;
    private ParticipantRepository participantRepository;
    private ProbaRepository probaRepository;
    private PersoanaOficiuRepository persoanaOficiuRepository;
    private Service service;
    private Properties props = new Properties();

    @Before
    public void setUp() throws Exception {
        props.load(new FileInputStream("bd.config"));
        repository = new InscriereRepository(props);
        participantRepository = new ParticipantRepository(props);
        probaRepository = new ProbaRepository(props);
        persoanaOficiuRepository = new PersoanaOficiuRepository(props);

        service = new Service(repository, participantRepository, persoanaOficiuRepository, probaRepository );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void numarParticipantiProbe() {
        service.numarParticipantiProbe();
    }
}