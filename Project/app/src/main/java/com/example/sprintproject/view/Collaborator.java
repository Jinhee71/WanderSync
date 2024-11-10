package com.example.sprintproject.view;

public class Collaborator {
    private String email;
    private String uid;

    public Collaborator(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
