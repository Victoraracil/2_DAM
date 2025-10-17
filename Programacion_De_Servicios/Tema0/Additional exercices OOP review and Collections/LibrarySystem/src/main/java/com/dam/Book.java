package com.dam;

public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return String.format("\"%s\" by %s", title, author);
    }

    // Needed for using Book as a HashMap key
    @Override
    public int hashCode() {
        return title.hashCode() + author.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return this.title.equals(other.title) && this.author.equals(other.author);
    }
}
