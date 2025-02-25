package Controller;

import Model.Dao.RentDAO;
import Model.Entities.Book;
import Model.Entities.LendingSystem;
import Model.Entities.Users;

import java.io.IOException;
import java.util.Date;

public class RentController {
    private RentDAO rentDAO;
    private UserController userController;
    private BookController bookController;

    public RentController() {
        rentDAO = new RentDAO();
        userController = new UserController();
        bookController = new BookController();
    }

    // Crear un nuevo préstamo
    public void createRent(String run, String isbn, int diasPrestamo) throws IOException {
        Users user = userController.searchUser(run);
        Book book  = bookController.findBook(isbn);

        if (user.havePendingBook()) {
            throw new IllegalArgumentException("El usuario ya tiene un préstamo activo.");
        }
        if (book.getTotalAmount() <= 0) {
            throw new IllegalArgumentException("No hay ejemplares disponibles para el libro.");
        }

        Date rentingDate = new Date();
        Date returnDate = calculateReturnDate(rentingDate, diasPrestamo);

        LendingSystem rents = new LendingSystem(run, isbn, rentingDate, returnDate);
        rentDAO.save(rents);

        user.setHavePendingBook(true);
        user.setIsbnRented(isbn);
        userController.updateUser(user);

        book.setTotalAmount(book.getTotalAmount() - 1);
        bookController.updateBook(book);
    }

    // Devolver un libro
    public void returnBook(String run, String isbn, Date realReturnDate) throws IOException {
        LendingSystem rent = rentDAO.findById(run);
        if (rent == null || !rent.getIsBnBook().equals(isbn)) {
            throw new IllegalArgumentException("Préstamo no encontrado para este usuario y libro.");
        }

        Users user = userController.searchUser(run);
        Book book = bookController.findBook(isbn);

        rentDAO.returnRentedBook(run, isbn, realReturnDate);

        user.setHavePendingBook(false);
        user.setIsbnRented(null);
        userController.updateUser(user);

        book.setQuantityAvailable(book.getQuantityAvailable() + 1);
        bookController.updateBook(book);
    }

    private Date calculateReturnDate(Date startDate, int days) {
        long returnTime = startDate.getTime() + ((long) days * 24 * 60 * 60 * 1000);
        return new Date(returnTime);
    }
}
