package Model.Dao;
import java.io.IOException;
import java.util.List;

public class BaseDAO<T> {
    void save(T entity) throws IOException {

    }

    List<T> getAll() throws IOException {
        return null;
    }

    T findById(String id) throws IOException {
        return null;
    }

    void delete(String id) throws IOException {

    }
}
