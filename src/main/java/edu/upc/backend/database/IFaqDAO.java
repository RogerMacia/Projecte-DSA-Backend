package edu.upc.backend.database;
import edu.upc.backend.database.*;
import edu.upc.backend.classes.*;

import java.sql.SQLException;
import java.util.*;

public interface IFaqDAO {
    public void addFaq(Faq faq) throws SQLException;
    public Faq getFaq(int faqId) throws SQLException;
    public List<Faq> getFaqs() throws SQLException;
    public void update(Faq faq) throws SQLException;
    public void delete(Faq faq) throws SQLException;
}
