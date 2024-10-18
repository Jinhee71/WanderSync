package com.example.sprintproject.model;

import java.time.LocalDateTime;

public class Note {
    private String noteContent;
    private User author;

    // Constructor
    public Note(String noteContent, User author) {
        this.noteContent = noteContent;
        this.author = author;
    }

    // Getters and Setters
    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

}
