package edu.upc.backend.database;

import edu.upc.backend.classes.UserItem;

import java.util.HashMap;
import java.util.List;

public interface IUserItemDAO {
    void addUserItem(UserItem userItem);
    List<UserItem> getUserItems(HashMap<String,Object> params);
    UserItem updateUserItem(UserItem userItem) throws Exception;
}
