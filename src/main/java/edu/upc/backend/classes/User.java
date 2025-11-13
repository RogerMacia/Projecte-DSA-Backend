package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;

public class User {

    // 🧱 Atributs
    private static int nextId = 0;
    private int id;
    private String username;
    private String nom;
    private String email;
    private String password;

    // 🔧 Constructor buit (necessari per frameworks o JSON)
    public User() {
        this.id = nextId++;
    }

    // 🔧 Constructor complet
    public User(String username,String nom,String email,String password) {
        this.id = nextId++;
        this.username = username;
        this.nom = nom;
        this.email = email;
        this.password = password;

    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // 🧾 Representació del client
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", password='" + password +'\'' +
                '}';
    }
}
