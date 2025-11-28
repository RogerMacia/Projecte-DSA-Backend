package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserList {
    private List<User> userslist;

    // 🔧 Constructor
    public UserList() {
        this.userslist = new ArrayList<>();
    }

    public UserList(List<User> list) {userslist = list;}

    // ➕ Afegir un nou client
    public void addUser(User user) {
        this.userslist.add(user);
    }

    // 🔍 Buscar un client per ID
    public User getUserByUsername(String username) {
        for (User c : userslist) {
            if (Objects.equals(c.getUsername(), username)) {
                return c;
            }
        }
        return null; // si no el troba
    }

    public User getUserById(int userId) {
        for (User c : userslist) {
            if (c.getId() == userId) {
                return c;
            }
        }
        return null;
    }

    public List<User> getUserslist() {
        return this.userslist;
    }

    public int size() {
        return this.userslist.size();
    }
}
