package edu.upc.backend.database;
import edu.upc.backend.database.*;
import edu.upc.backend.classes.*;

import java.sql.SQLException;
import java.util.*;

public interface IFaqDAO {
    public void add(Faq faq) throws SQLException;
    public Faq get(int faqId) throws SQLException;
    public List<Faq> getAll() throws SQLException;
    public void update(Faq faq) throws SQLException;
    public void delete(Faq faq) throws SQLException;
}
