package com.example.felipelevez.teste.models;

public class User {

    private String name;
    private String email;
    private String phone;
    private int id;

    public User(int id, String name, String email, String phone) {
        this.id = id;
        this.name =name;
        this.email = email;
        this.phone=phone;
    }
    public User(String name, String email, String phone) {
        this.name =name;
        this.email = email;
        this.phone=phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
