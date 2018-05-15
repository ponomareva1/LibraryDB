package com.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty passportSeria;
    private StringProperty passportNum;

    public Client() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.passportSeria = new SimpleStringProperty();
        this.passportNum = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int clientId){
        this.id.set(clientId);
    }

    public IntegerProperty idProperty(){
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String clientFirstName){
        this.firstName.set(clientFirstName);
    }

    public StringProperty firstNameProperty(){
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String clientLastName){
        this.lastName.set(clientLastName);
    }

    public StringProperty lastNameProperty(){
        return lastName;
    }

    public String getPassportSeria() {
        return passportSeria.get();
    }

    public void setPassportSeria(String clientPassportSeria){
        this.passportSeria.set(clientPassportSeria);
    }

    public StringProperty passportSeriaProperty(){
        return passportSeria;
    }

    public String getPassportNum() {
        return passportNum.get();
    }

    public void setPassportNum(String clientPassportNum){
        this.passportNum.set(clientPassportNum);
    }

    public StringProperty passportNumProperty(){
        return passportNum;
    }
}
