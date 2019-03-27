package repository;

public interface IPersoanaOficiuRepository {
    public boolean findByUsernameAndPassword(String username, String password);
}
