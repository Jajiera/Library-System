package Model.Dao;
import Model.Entities.LendingSystem;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RentDAO extends BaseDAO<LendingSystem> {
    private static final String FILE_PATH = "resources/data/prestamos.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void save(LendingSystem rents) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(convertRentToString(rents));
            writer.newLine();
        }
    }

    @Override
    public List<LendingSystem> getAll() throws IOException {
        List<LendingSystem> rents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rents.add(convertStringToRent(line));
            }
        }
        return rents;
    }

    @Override
    public LendingSystem findById(String run) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LendingSystem rent = convertStringToRent(line);
                if (rent.getUserRut().equals(run)) {
                    return rent;
                }
            }
        }
        return null;
    }

    @Override
    public void delete(String id) throws IOException {
        File inputFile = new File(FILE_PATH);
        File tempFile = new File("resources/prestamos_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                LendingSystem rent = convertStringToRent(line);
                if (!rent.getUserRut().equals(id)) {
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

    public void returnRentedBook(String run, String isbn, Date returnDate) throws IOException {
        List<LendingSystem> rentedBooks = getAll();
        LendingSystem rentFound = null;

        for (LendingSystem rent : rentedBooks) {
            if (rent.getUserRut().equals(run) && rent.getIsBnBook().equals(isbn)) {
                rentFound = rent;
                break;
            }
        }

        if (rentFound != null) {
            rentedBooks.remove(rentFound);
            double penalty = calculatePenalization(rentFound.getReturningDate(), returnDate);
            if (penalty > 0) {
                System.out.println("Multa por retraso: $" + penalty);
            }
            saveAll(rentedBooks);
        } else {
            throw new IOException("Préstamo no encontrado para devolución");
        }
    }

    private void saveAll(List<LendingSystem> rents) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (LendingSystem rent : rents) {
                writer.write(convertRentToString(rent));
                writer.newLine();
            }
        }
    }

    private double calculatePenalization(Date expectedDate, Date trueReturnDate) {
        if (trueReturnDate.after(expectedDate)) {
            long difference = trueReturnDate.getTime() - expectedDate.getTime();
            long lateDays = difference / (1000 * 60 * 60 * 24);
            return lateDays * 1000;
        }
        return 0;
    }

    private String convertRentToString(LendingSystem rent) {
        return rent.getUserRut() + "|" +
                rent.getIsBnBook() + "|" +
                DATE_FORMAT.format(rent.getRentingDate()) + "|" +
                DATE_FORMAT.format(rent.getRentingDate()) + "|" +
                rent.getBookFine();
    }

    private LendingSystem convertStringToRent(String linea) throws IOException {
        String[] data = linea.split("\\|");
        String userRut = data[0];
        String isbnBook = data[1];
        Date lendingDate;
        Date returningDate;
        double penalty;

        try {
            lendingDate = DATE_FORMAT.parse(data[2]);
            returningDate = DATE_FORMAT.parse(data[3]);
            penalty = Double.parseDouble(data[4]);
        } catch (Exception e) {
            throw new IOException("Error al parsear las fechas o la multa", e);
        }

        LendingSystem rent = new LendingSystem(userRut, isbnBook, lendingDate, returningDate);
        rent.setBookFine(penalty);
        return rent;
    }
}
