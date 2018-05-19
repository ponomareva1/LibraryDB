package com.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookType {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty fine;
    private IntegerProperty dayCount;

    public BookType() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.fine = new SimpleIntegerProperty();
        this.dayCount = new SimpleIntegerProperty();
    }

    public Integer getId() { return id.get(); }

    public void setId(int id){ this.id.set(id); }

    public IntegerProperty idProperty(){ return id; }

    public String getName() { return name.get(); }

    public void setName(String name){ this.name.set(name); }

    public StringProperty nameProperty(){ return name; }

    public Integer getFine() {
        return fine.get();
    }

    public void setFine(Integer fine){
        this.fine.set(fine);
    }

    public IntegerProperty fineProperty(){
        return fine;
    }

    public Integer getDayCount() {
        return dayCount.get();
    }

    public void setDayCount(Integer dayCount){
        this.dayCount.set(dayCount);
    }

    public IntegerProperty dayCountProperty(){
        return dayCount;
    }
}
