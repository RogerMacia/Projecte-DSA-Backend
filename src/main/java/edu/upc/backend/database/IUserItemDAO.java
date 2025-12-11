package edu.upc.backend.database;

import edu.upc.backend.classes.UserItem;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IUserItemDAO {
    void addUserItem(UserItem userItem) throws SQLException;
    List<UserItem> getUserItems(int userId) throws SQLException;
    void updateUserItem(UserItem userItem) throws Exception;
}
