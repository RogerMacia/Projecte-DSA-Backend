package edu.upc.backend.database;

import edu.upc.backend.classes.UserItem;

public interface IUserItemDAO {
    void addUserItem(UserItem userItem);
    UserItem getUserItem(int userId, int itemId);
    UserItem updateUserItem(UserItem userItem) throws Exception;
}
