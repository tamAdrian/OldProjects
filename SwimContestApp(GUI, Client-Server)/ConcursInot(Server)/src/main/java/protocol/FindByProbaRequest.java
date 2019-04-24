package protocol;

import model.ProbaDTO;

public class FindByProbaRequest implements Protocol {

    private Object idProbaDTO;

    public FindByProbaRequest(Object probaDTO) {
        this.idProbaDTO = probaDTO;
    }

    @Override
    public String objectToString() {
        Integer idProbaDTO = (Integer) this.idProbaDTO;
        String string = "";
        string += RequestString.FINDBYPROBA + "/" + idProbaDTO;
        return string;
    }

    @Override
    public Object stringToObject() {
        String string = (String)this.idProbaDTO;
        String[] parts = string.split("/");
        return Integer.parseInt(parts[1]);
    }
}
