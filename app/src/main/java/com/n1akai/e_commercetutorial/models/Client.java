package com.n1akai.e_commercetutorial.models;

public class Client {
    private String id, firstName, lastName, email, adresse, phoneNumber;

    public Client() {
    }

    public Client(String id, String firstName, String lastName, String email, String adresse, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.adresse = adresse;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
