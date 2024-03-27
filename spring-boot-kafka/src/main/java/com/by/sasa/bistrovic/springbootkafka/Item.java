package com.by.sasa.bistrovic.springbootkafka;

public class Item {
    private String message;

    // Default constructor
    public Item() {
    }

    // Parameterized constructor
    public Item(String message) {
        this.message = message;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }
}