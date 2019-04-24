package protocol;

import model.ParticipantDTO;
import model.PersoanaOficiu;
import model.ProbaDTO;
import service.Service;

import java.util.List;

public class Request {
    private RequestString requestString;
    private String string;
    private Protocol protocol;
    private Service service;

    public Request(String string, Service service) {
        this.string = string;
        this.service = service;
        String p[] = string.split("/");
        this.requestString = RequestString.valueOf(p[0]);

    }

    public Protocol response() {
        switch (this.requestString){
            case LOGIN:
                this.protocol = new LoginRequest(string);
                return loginResponse();
            case INSCRIE:
                this.protocol = new InscrieRequest(string);
                return inscrieResponse();
            case FINDALL:
                this.protocol = new FindAllRequest();
                return findAllResponse();
            case FINDBYPROBA:
                this.protocol = new FindByProbaRequest(string);
                return findByProbaResponse();
            case LOGOUT:
                this.protocol = new LogoutRequest();
                return logoutRequest();
        }
        return null;
    }

    private Protocol logoutRequest() {
        return new LogoutRequest();
    }

    private Protocol findByProbaResponse() {
        int id = (Integer) protocol.stringToObject();
        List<ParticipantDTO> list = service.cautaParticipantiProbe(id);
        FindByProbaDTO dto = new FindByProbaDTO(list);
        return new FindByProbaResponse(dto);
    }

    private Protocol findAllResponse() {
        List<ProbaDTO> list = service.numarParticipantiProbe();
        FindAllDTO dto = new FindAllDTO(list);
        return new FindAllResponse(dto);
    }

    private Protocol inscrieResponse() {
        InscrieDTO inscrieDTO = (InscrieDTO) protocol.stringToObject();
        service.inscrieParticipant(inscrieDTO.getNume(), inscrieDTO.getVarsta(), inscrieDTO.getList());
        return new InscrieResponse("Inscriere efectuata cu succes !");
    }

    private Protocol loginResponse() {
        PersoanaOficiu oficiu = (PersoanaOficiu) protocol.stringToObject();
        Boolean validate = service.login(oficiu.getID(), oficiu.getPassword());
        return new LoginResponse(validate);
    }
}
