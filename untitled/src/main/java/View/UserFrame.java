package View;

import Controller.UserController;
import Model.Entities.Student;
import Model.Entities.Teacher;
import Model.Entities.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame {
    private UserController userController;

    public UserFrame() {
        userController = new UserController();

        setTitle("Gestión de Usuarios");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Campos y botones
        JTextField txtRun = new JTextField();
        JButton btnBuscar = new JButton("Buscar Usuario");
        JButton btnAgregar = new JButton("Agregar Usuario");
        JTextArea txtAreaResultado = new JTextArea();

        // Evento para buscar usuario
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String run = txtRun.getText();
                    Users user = userController.searchUser(run);
                    txtAreaResultado.setText("Nombre: " + user.getFullName() + "\n"
                            + "RUN: " + user.getRun() + "\n"
                            + "Género: " + user.getGenre() + "\n"
                            + "Tipo: " + user.getUserType());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserFrame.this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento para agregar usuario
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = JOptionPane.showInputDialog("Nombre Completo:");
                    String run = JOptionPane.showInputDialog("RUN:");
                    String genre = JOptionPane.showInputDialog("Género (M/F):");
                    String type = JOptionPane.showInputDialog("Tipo (Docente/Estudiante):");

                    if (type.equalsIgnoreCase("Docente")) {
                        String grades = JOptionPane.showInputDialog("Grados Académicos:");
                        userController.createUser(new Teacher(name, run, genre, grades));
                    } else if (type.equalsIgnoreCase("Estudiante")) {
                        String carrera = JOptionPane.showInputDialog("Carrera:");
                        userController.createUser(new Student(name, run, genre, carrera));
                    }

                    JOptionPane.showMessageDialog(UserFrame.this, "Usuario agregado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserFrame.this, "Error al agregar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Añadir componentes al frame
        add(new JLabel("RUN del Usuario:"));
        add(txtRun);
        add(btnBuscar);
        add(btnAgregar);
        add(new JScrollPane(txtAreaResultado));
    }
}
