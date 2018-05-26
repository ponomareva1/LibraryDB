package com.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Journal {
    private IntegerProperty id;
    private StringProperty bookId;
    private StringProperty clientId;
    private StringProperty dateBeg;
    private StringProperty dateEnd;
    private StringProperty dateRet;

    public Journal() {
        this.id = new SimpleIntegerProperty();
        this.bookId = new SimpleStringProperty();
        this.clientId = new SimpleStringProperty();
        this.dateBeg = new SimpleStringProperty();
        this.dateEnd = new SimpleStringProperty();
        this.dateRet = new SimpleStringProperty();
    }

    public Integer getId() { return id.get(); }

    public void setId(int id){ this.id.set(id); }

    public IntegerProperty idProperty(){ return id; }

    public String getBookId() { return bookId.get(); }

    public void setBookId(String bookId){ this.bookId.set(bookId); }

    public StringProperty bookIdProperty(){ return bookId; }

    public String getClientId() { return clientId.get(); }

    public void setClientId(String clientId){ this.clientId.set(clientId); }

    public StringProperty clientIdProperty(){ return clientId; }

    public String getDateBeg() { return dateBeg.get(); }

    public void setDateBeg(String dateBeg){ this.dateBeg.set(dateBeg); }

    public StringProperty dateBegProperty(){ return dateBeg; }

    public String getDateEnd() { return dateEnd.get(); }

    public void setDateEnd(String dateEnd){ this.dateEnd.set(dateEnd); }

    public StringProperty dateEndProperty(){ return dateEnd; }

    public String getDateRet() { return dateRet.get(); }

    public void setDateRet(String dateRet){ this.dateRet.set(dateRet); }

    public StringProperty dateRetProperty(){ return dateRet; }
}
