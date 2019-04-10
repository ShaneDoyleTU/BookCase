package edu.temple.bookcase;

public class Book {
    int bookId;
    String bookTitle;
    String author;
    int published;
    String coverURL;

    public Book() {
    }

    public int getBookId(){
        return bookId;
    }

    public void setBookId(int id){
        this.bookId=id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }
}
