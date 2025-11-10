package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersList {
    private List<User> userslist;

    // 🔧 Constructor
    public UsersList() {
        this.userslist = new ArrayList<>();
    }

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

    public List<User> getUserslist() {
        return this.userslist;
    }

    public int size() {
        return this.userslist.size();
    }
}
