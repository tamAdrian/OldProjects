package proxy;

import controller.MainMenuController;
import model.ParticipantDTO;
import model.PersoanaOficiu;
import model.ProbaDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Proxy {

    private String host;
    private int port;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<String> qresponses;
    private volatile boolean finished;

    //access to main window for update
    private MainMenuController controller;
    public void setController(MainMenuController controller){
        this.controller = controller;
    }

    public Proxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<>();
    }

    public boolean login(PersoanaOficiu oficiu) {
        Protocol request = new LoginRequest(oficiu);
        sendRequest(request.objectToString());
        String string = readResponse();
        LoginResponse response = new LoginResponse(string);
        Boolean validate = response.stringToObject();
        return validate;
    }

    public void inscrieParticipant(String nume, Integer varsta, List<ProbaDTO> list) {
        InscrieDTO inscrieDTO = new InscrieDTO(nume, varsta, list);
        Protocol request = new InscrieRequest(inscrieDTO);
        sendRequest(request.objectToString());
        String string = readResponse();
        System.out.println(string);
    }

    public List<ProbaDTO> numarParticipantiProbe() {
        initializeConnection();
        Protocol request = new FindAllRequest();
        sendRequest(request.objectToString());
        String response = readResponse();
        FindAllResponse findAllResponse = new FindAllResponse(response);
        FindAllDTO dto = (FindAllDTO) findAllResponse.stringToObject();
        return dto.getList();
    }

    public List<ParticipantDTO> cautaParticipantiProbe(Integer id) {
        Protocol request = new FindByProbaRequest(id);
        sendRequest(request.objectToString());
        String response = readResponse();
        FindByProbaResponse findByProbaResponse = new FindByProbaResponse(response);
        FindByProbaDTO dto = (FindByProbaDTO) findByProbaResponse.stringToObject();
        return dto.getList();
    }


    public void logout() {
        Protocol request = new LogoutRequest();
        sendRequest(request.objectToString());
        String response = readResponse();
        LogoutRequest logoutResponse = new LogoutRequest();
        System.out.println(logoutResponse.objectToString());
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(String request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private String readResponse()  {
        String response = null;
        try{
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private class ReaderThread implements Runnable{

        public void run() {
            while( !finished ){
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    String str = (String) response;

                    String[] update = str.split("/");
                    if(update.length > 1){
                        if(update[0].equals("UPDATE")){
                            Protocol resp = new UpdateResponse(str);
                            FindAllDTO dto = (FindAllDTO) resp.stringToObject();
                            controller.update(dto.getList());
                            continue;
                        }

                    }
                        try {
                            qresponses.put((String) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

}
