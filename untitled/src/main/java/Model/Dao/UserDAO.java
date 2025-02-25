package Model.Dao;
import Model.Entities.Student;
import Model.Entities.Teacher;
import Model.Entities.Users;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class UserDAO extends BaseDAO<Users> {
    private static final String FILE_PATH = "resources/users.txt";

    @Override
    public void save(Users user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(convertUserToString(user));
            writer.newLine();
        }
    }
    @Override
    public List<Users> getAll() throws IOException{
        List<Users> users = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while ((line = reader.readLine()) != null){
                users.add(convertStringToUser(line));
                }
            }
        return users;
        }

    public Users findById(String run) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Users users = convertStringToUser(line);
                if (users.getRun().equals(run)) {
                    return users;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(String run) throws IOException {
        File inputFile = new File(FILE_PATH);
        File tempFile = new File("resources/users_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                Users users = convertStringToUser(line);
                if (!users.getRun().equals(run)) {
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
    
    private String convertUserToString(Users users) {
        String type = users instanceof Teacher ? "Docente" : "Estudiante";
        StringBuilder sb = new StringBuilder();
        sb.append(type).append("|")
                .append(users.getFullName()).append("|")
                .append(users.getRun()).append("|")
                .append(users.getGenre()).append("|")
                .append(users.havePendingBook() ? users.getIsbnRented() : "NO");

        if (users instanceof Teacher) {
            sb.append("|").append(((Teacher) users).getAcademicGrade());
        } else if (users instanceof Student) {
            sb.append("|").append(((Student) users).getCareer());
        }

        return sb.toString();
    }
    
    private Users convertStringToUser(String line) {
        String[] data = line.split("\\|");
        String type = data[0];
        String fullName = data[1];
        String run = data[2];
        String genre = data[3];
        boolean isRenting = !data[4].equals("NO");
        String isbnRented = isRenting ? data[4] : null;

        if (type.equals("Docente")) {
            String academicGrade = data[5];
            Teacher teacher = new Teacher(fullName, run, genre, academicGrade);
            teacher.setHavePendingBook(isRenting);
            teacher.setIsbnRented(isbnRented);
            return teacher;
        } else if (type.equals("Estudiante")) {
            String career = data[5];
            Student student = new Student(fullName, run, genre, career);
            student.setHavePendingBook(isRenting);
            student.setIsbnRented(isbnRented);
            return student;
        }

        return null;
    }
}
