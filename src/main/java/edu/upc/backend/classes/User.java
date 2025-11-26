package edu.upc.backend.classes;

public class User {

    // 🧱 Atributs
    private static int nextId = 0;
    private int id;
    private String username;
    private String name;
    private String email;
    private String password;
    private int score;

    // 🔧 Constructor buit (necessari per frameworks o JSON)
    public User() {
        this.id = nextId++;
        this.score = 0;
    }

    // 🔧 Constructor complet
    public User(String username, String name, String email, String password) {
        this.id = nextId++;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.score = 0;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
