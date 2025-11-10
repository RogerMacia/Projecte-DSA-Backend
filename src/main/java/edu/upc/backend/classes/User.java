package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;

public class User {

    // 🧱 Atributs
    private static int nextId = 1;
    private int id;
    private String username;
    private String nom;
    private String cognom1;
    private String cognom2;
    private String email;
    private String password;
    private String datanaixement;

    // 🔧 Constructor buit (necessari per frameworks o JSON)
    public User() {
        this.id = nextId++;
    }

    // 🔧 Constructor complet
    public User(String username,String nom,String cognom1,String cognom2,String email,String password,String datanaixement) {
        this.id = nextId++;
        this.username = username;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.email = email;
        this.password = password;
        this.datanaixement = datanaixement;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatanaixement() {
        return datanaixement;
    }

    public void setDatanaixement(String datanaixement) {
        this.datanaixement = datanaixement;
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
                ", cognom1='" + cognom1 + '\'' +
                ", cognom2='" + cognom2 + '\'' +
                ", datanaixement='" + datanaixement + '\'' +
                '}';
    }
}
