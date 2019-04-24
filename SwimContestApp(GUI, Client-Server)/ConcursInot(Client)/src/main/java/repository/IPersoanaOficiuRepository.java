package repository;

/*
define own methods
*/
public interface IPersoanaOficiuRepository {
    public boolean findByUsernameAndPassword(String username, String password);
}
