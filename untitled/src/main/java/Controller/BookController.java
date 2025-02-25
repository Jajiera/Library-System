package Controller;

import Model.Dao.BookDAO;
import Model.Entities.Book;

import java.io.IOException;
import java.util.List;

public class BookController {
    private final BookDAO bookDAO;

    public BookController() {
        bookDAO = new BookDAO();
    }
    // Crear un nuevo libro
    public void createBook(Book book) throws IOException {
        if (bookDAO.findById(book.getIsbn()) != null) {
            throw new IllegalArgumentException("El ISBN ya existe en el sistema.");
        }
        if (book.getTotalAmount() <= 0) {
            throw new IllegalArgumentException("La cantidad total debe ser mayor a 0.");
        }
        if (book.getQuantityAvailable() < 0 || book.getQuantityAvailable() > book.getTotalAmount()) {
            throw new IllegalArgumentException("Cantidad disponible debe estar entre 0 y la cantidad total.");
        }
        bookDAO.save(book);
    }

    // Obtener todos los libros
    public List<Book> getBooks() throws IOException {
        return bookDAO.getAll();
    }

    // Buscar libro por ISBN
    public Book findBook(String isbn) throws IOException {
        Book book = bookDAO.findById(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Libro no encontrado.");
        }
        return book;
    }

    // Eliminar libro por ISBN
    public void deleteBook(String isbn) throws IOException {
        if (bookDAO.findById(isbn) == null) {
            throw new IllegalArgumentException("Libro no encontrado.");
        }
        bookDAO.delete(isbn);
    }

    // Actualizar un libro existente
    public void updateBook(Book book) throws IOException {
        deleteBook(book.getIsbn());
        createBook(book);
    }

}