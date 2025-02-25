package Model.Entities;

public abstract class Users {
    private String fullName;
    private String run;
    private String genre; // M para masculino, F para femenino
    private boolean havePendingBook; // false si no tiene un libro prestado
    private String isbnRented; // ISBN del libro prestado, null si no tiene préstamo

    public Users(String fullName, String run, String genre) {
        this.fullName = fullName;
        this.run = run;
        this.genre = genre;
        this.havePendingBook = false;
        this.isbnRented = null;
    }

    public abstract String getUserType();

    // Getters y Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean havePendingBook() {
        return havePendingBook;
    }

        public void setHavePendingBook(boolean havePendingBook) {
        this.havePendingBook = havePendingBook;
    }

    public String getIsbnRented() {
        return isbnRented;
    }

    public void setIsbnRented(String isbnRented) {
        this.isbnRented = isbnRented;
    }
}
