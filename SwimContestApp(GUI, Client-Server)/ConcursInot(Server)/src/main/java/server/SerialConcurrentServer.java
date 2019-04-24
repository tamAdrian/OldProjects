package server;
import service.Service;
import java.net.Socket;

public class SerialConcurrentServer extends ConcurrentServer {

    private Service service;

    public SerialConcurrentServer(int port, Service service) {
        super(port);
        this.service = service;
        System.out.println("SerialConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        Worker worker = new Worker(client, service);
        service.addObserver(worker);
        return new Thread(worker);
    }

}

