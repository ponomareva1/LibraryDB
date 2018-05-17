package com.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private IntegerProperty id;
    private StringProperty title;
    private StringProperty author;
    private IntegerProperty count;
    private IntegerProperty type;

    public Book() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.count = new SimpleIntegerProperty();
        this.type = new SimpleIntegerProperty();
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(int clientId){
        this.id.set(clientId);
    }

    public IntegerProperty idProperty(){
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String bookTitle){
        this.title.set(bookTitle);
    }

    public StringProperty titleProperty(){
        return title;
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String bookAuthor){
        this.author.set(bookAuthor);
    }

    public StringProperty authorProperty(){
        return author;
    }

    public Integer getCount() {
        return count.get();
    }

    public void setCount(Integer bookCount){
        this.count.set(bookCount);
    }

    public IntegerProperty countProperty(){
        return count;
    }

    public Integer getType() {
        return type.get();
    }

    public void setType(Integer bookType){
        this.type.set(bookType);
    }

    public IntegerProperty typeProperty(){
        return type;
    }
}
