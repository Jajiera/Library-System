package Controller;
import Model.Dao.UserDAO;
import Model.Entities.Users;
import java.io.IOException;
import java.util.List;


public class UserController {

    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    // Crear un nuevo usuario
    public void createUser(Users user) throws IOException {
        if (userDAO.findById(user.getRun()) != null) {
            throw new IllegalArgumentException("El RUN ya existe en el sistema.");
        }
        userDAO.save(user);
    }

    // Obtener todos los usuarios
    public List<Users> getUsers() throws IOException {
        return userDAO.getAll();
    }

    // Buscar usuario por RUN
    public Users searchUser(String run) throws IOException {
        Users user = userDAO.findById(run);
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        return user;
    }

    // Eliminar usuario
    public void deleteUser(String run) throws IOException {
        if (userDAO.findById(run) == null) {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
        userDAO.delete(run);
    }

    // Actualizar un usuario existente
    public void updateUser(Users user) throws IOException {
        deleteUser(user.getRun());
        createUser(user);
    }
}
