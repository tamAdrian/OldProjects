package server;
import model.ProbaDTO;
import protocol.FindAllDTO;
import protocol.Protocol;
import protocol.Request;
import protocol.UpdateResponse;
import service.Service;
import utils.IObserver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class Worker implements Runnable, IObserver {

    private Socket client;
    private Service service;
    private volatile boolean connected;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    Worker(Socket client, Service service) {
        this.client = client;
        this.service = service;
        this.connected = true;
    }

    public void run() {
            try {
                System.out.println("Starting to process request ...  ");
                out  = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                in = new ObjectInputStream(client.getInputStream());

                while (connected) {
                    try {
                        Object obj = in.readObject();
                        if (obj instanceof String) {
                            System.out.println((String) obj);
                            Request request = new Request((String) obj, service);
                            Protocol response = request.response();
                            out.writeObject(response.objectToString());
                            out.flush();
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error deserializing " + e);
                    }
                    System.out.println("Finished  processing request ...");
                }
            }
            catch (IOException e) {
                System.out.println("Error in processing client request " + e);
            }

    }

    @Override
    public void update() {
        try{

                List<ProbaDTO> list = service.numarParticipantiProbe();
                FindAllDTO dto = new FindAllDTO(list);
                Protocol resp = new UpdateResponse(dto);

                out.writeObject(resp.objectToString());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
