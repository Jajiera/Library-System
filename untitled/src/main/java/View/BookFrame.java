package View;

import Controller.BookController;
import Model.Entities.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookFrame extends JFrame {
    private BookController bookController;

    public BookFrame() {
        bookController = new BookController();

        setTitle("Gestión de Libros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Campos y botones
        JTextField txtIsbn = new JTextField();
        JButton btnFind = new JButton("Buscar Libro");
        JButton btnAdd = new JButton("Agregar Libro");
        JTextArea txtAreaResult = new JTextArea();

        // Evento para buscar libro
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String isbn = txtIsbn.getText();
                    Book book = bookController.findBook(isbn);
                    txtAreaResult.setText("Título: " + book.getTitle() + "\n"
                            + "Autor: " + book.getAuthor() + "\n"
                            + "Cantidad Disponible: " + book.getQuantityAvailable());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(BookFrame.this, "Libro no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento para agregar libro
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String isbn = JOptionPane.showInputDialog("ISBN:");
                    String title = JOptionPane.showInputDialog("Título:");
                    String author = JOptionPane.showInputDialog("Autor:");
                    int totalAmount = Integer.parseInt(JOptionPane.showInputDialog("Cantidad Total:"));
                    int stock = Integer.parseInt(JOptionPane.showInputDialog("Cantidad Disponible:"));

                    bookController.createBook(new Book(isbn, title, author, totalAmount, stock, null));
                    JOptionPane.showMessageDialog(BookFrame.this, "Libro agregado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(BookFrame.this, "Error al agregar libro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir componentes al frame
        add(new JLabel("ISBN del Libro:"));
        add(txtIsbn);
        add(btnFind);
        add(btnAdd);
        add(new JScrollPane(txtAreaResult));
    }
}
