package com.dam.Tema0.Ejercicio31;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public Usuario(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getUserId() { return userId; }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }

    @Override
    public String toString() {
        return "Usuario{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
