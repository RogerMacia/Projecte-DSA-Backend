package edu.upc.backend.database;

import edu.upc.backend.classes.Item;

import java.util.List;

public interface IItemDAO {
    public List<Item> getItemlist() throws Exception;

}
