package View;

import Controller.RentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class RentFrame extends JFrame {
    private RentController rentController;

    public RentFrame() {
        rentController = new RentController();

        setTitle("Gestión de Préstamos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        // Campos y botones
        JTextField txtRun = new JTextField();
        JTextField txtIsbn = new JTextField();
        JTextField txtDias = new JTextField();
        JButton btnRegistrarPrestamo = new JButton("Registrar Préstamo");
        JButton btnDevolverLibro = new JButton("Devolver Libro");
        JTextArea txtAreaResultado = new JTextArea();

        // Evento para registrar préstamo
        btnRegistrarPrestamo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String run = txtRun.getText();
                    String isbn = txtIsbn.getText();
                    int days = Integer.parseInt(txtDias.getText());

                    rentController.createRent(run, isbn, days);
                    txtAreaResultado.setText("Préstamo registrado exitosamente.\nRUN: " + run + "\nISBN: " + isbn);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RentFrame.this, "Error al registrar el préstamo: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento para devolver un libro
        btnDevolverLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String run = txtRun.getText();
                    String isbn = txtIsbn.getText();
                    Date returnDate = new Date(); // Fecha actual como devolución

                    rentController.returnBook(run, isbn, returnDate);
                    txtAreaResultado.setText("Libro devuelto exitosamente.\nRUN: " + run + "\nISBN: " + isbn);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RentFrame.this, "Error al devolver el libro: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir componentes al frame
        add(new JLabel("RUN del Usuario:"));
        add(txtRun);
        add(new JLabel("ISBN del Libro:"));
        add(txtIsbn);
        add(new JLabel("Cantidad de días del préstamo:"));
        add(txtDias);
        add(btnRegistrarPrestamo);
        add(btnDevolverLibro);
        add(new JScrollPane(txtAreaResultado));
    }
}
