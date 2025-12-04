package edu.upc.backend.classes;

import edu.upc.backend.util.*;

public class User {

    // 🧱 Atributs
    private int id;
    private String username;
    private String name;
    private String email;
    private String password;
    private int coins;

    // 🔧 Constructor buit (necessari per frameworks o JSON)
    public User() {
        this.id = DBUtils.retrieveUserID();
    }

    // 🔧 Constructor complet
    public User(String username, String name, String email, String password) {
        this.id = 0;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coins = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }


    // 🧾 Representació del client
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + name + '\'' +
                ", password='" + password +'\'' +
                '}';
    }
}
