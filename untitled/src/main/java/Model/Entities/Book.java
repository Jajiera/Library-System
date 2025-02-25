package Model.Entities;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private int totalAmount;
    private int quantityAvailable;
    private String image;

    public Book(String isbn, String title, String author, int totalAmount, int quantityAvailable, String image) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalAmount = totalAmount;
        this.quantityAvailable = quantityAvailable;
        this.image = image;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
    public int getQuantityAvailable() {
        return quantityAvailable;
    }
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
