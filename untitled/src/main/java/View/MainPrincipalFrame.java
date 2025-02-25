package View;
import View.BookFrame;
import View.UserFrame;
import View.RentFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPrincipalFrame extends JFrame {
    public MainPrincipalFrame() {
        setTitle("Sistema de Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        JButton btnUsers = new JButton("Gestión de Usuarios");
        JButton btnBooks = new JButton("Gestión de Libros");
        JButton btnRentBook = new JButton("Gestión de Préstamos");


        btnUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserFrame().setVisible(true);
            }
        });

        btnBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookFrame().setVisible(true);
            }
        });

        btnRentBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RentFrame().setVisible(true);
            }
        });

        // Añadir los botones al frame
        add(btnUsers);
        add(btnBooks);
        add(btnRentBook);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainPrincipalFrame().setVisible(true);
        });
    }
}
