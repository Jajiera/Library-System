package Model.Dao;
import Model.Entities.Book;

import java.io.*;
import java.util.*;

public class BookDAO extends BaseDAO<Book> {
    private static final String FILE_PATH = "resources/Books.txt";

    @Override
    public void save(Book book) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(convertBookToString(book));
            writer.newLine();
        }
    }

    @Override
    public List<Book> getAll() throws IOException {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                books.add(convertStringToBook(line));
            }
        }
        return books;
    }
    @Override
    public Book findById(String isbn) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = convertStringToBook(line);
                if (book.getIsbn().equals(isbn)) {
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(String isbn) throws IOException {
        File inputFile = new File(FILE_PATH);
        File tempFile = new File("resources/Books_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Book book = convertStringToBook(line);
                if (!book.getIsbn().equals(isbn)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        if (!inputFile.delete()) {
            throw new IOException("No se pudo eliminar el archivo original");
        }
        if (!tempFile.renameTo(inputFile)) {
            throw new IOException("No se pudo renombrar el archivo temporal");
        }
    }

    private String convertBookToString(Book book) {
        return book.getIsbn() + "|" + book.getTitle() + "|" + book.getAuthor() + "|" +
                book.getTotalAmount() + "|" + book.getQuantityAvailable() + "|" + book.getImage();
    }

    private static Book convertStringToBook(String line) {
        String[] data = line.split("\\|");
        String isbn = data[0];
        String title = data[1];
        String author = data[2];
        int totalAmount = Integer.parseInt(data[3]);
        int stock = Integer.parseInt(data[4]);
        String image = data[5];

        return new Book(isbn, title, author, totalAmount, stock, image);
    }
}
