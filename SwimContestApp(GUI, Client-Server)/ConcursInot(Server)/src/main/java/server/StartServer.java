package server;

import repository.InscriereRepository;
import repository.ParticipantRepository;
import repository.PersoanaOficiuRepository;
import repository.ProbaRepository;
import service.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(new FileInputStream("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        InscriereRepository inscriereRepository = new InscriereRepository(props);
        ParticipantRepository participantRepository = new ParticipantRepository(props);
        PersoanaOficiuRepository persoanaOficiuRepository = new PersoanaOficiuRepository(props);
        ProbaRepository probaRepository = new ProbaRepository(props);

        Service service = new Service(inscriereRepository, participantRepository, persoanaOficiuRepository, probaRepository);
        AbstractServer server = new SerialConcurrentServer(55555, service);
        try {
            server.start();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
