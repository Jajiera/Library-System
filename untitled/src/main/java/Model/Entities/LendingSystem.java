package Model.Entities;
import java.util.Date;

public class LendingSystem {
    private String userRut;
    private String isBnBook;
    private Date rentingDate;
    private Date returningDate;
    private double bookFine;

    public LendingSystem(String userRut, String isBnBook, Date rentingDate, Date returningDate) {
        this.userRut = userRut;
        this.isBnBook = isBnBook;
        this.rentingDate = rentingDate;
        this.returningDate = returningDate;
        this.bookFine = 0.0;
    }
    public String getUserRut() {
        return userRut;
    }
    public void setUserRut(String userRut) {
        this.userRut = userRut;
    }
    public String getIsBnBook() {
        return isBnBook;
    }
    public void setIsBnBook(String isBnBook) {
        this.isBnBook = isBnBook;
    }
    public Date getRentingDate() {
        return rentingDate;
    }
    public void setRentingDate(Date rentingDate) {
        this.rentingDate = rentingDate;
    }
    public Date getReturningDate() {
        return returningDate;
    }
    public void setReturningDate(Date returningDate) {
        this.returningDate = returningDate;
    }
    public double getBookFine() {
        return bookFine;
    }
    public void setBookFine(double bookFine) {
        this.bookFine = bookFine;
    }
}
