package edu.upc.backend.database;
import java.sql.SQLException;
import java.util.*;
import edu.upc.backend.database.util.Utils;

import edu.upc.backend.classes.Faq;

import java.util.List;

public class FaqDAO implements IFaqDAO{

    private static FaqDAO _instance;
    public static FaqDAO getInstance()
    {
        if(_instance == null) _instance = new FaqDAO();
        return _instance;
    }

    @Override
    public void add(Faq faq) throws SQLException {
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.save(faq);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public Faq get(int faqId) throws SQLException {

        Session session = null;
        Faq res = null;
        try{
            session = new SessionBuilder().build();
            res = (Faq) session.get(Faq.class,faqId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return res;
    }

    @Override
    public List<Faq> getAll() throws SQLException {
        Session session = null;
        List<Faq> res = null;
        try{
            session = new SessionBuilder().build();
            res = Utils.<Faq>castList(session.findAll(Faq.class));
        }
        catch (Exception e)
        {
            e.printStackTrace();;
        }
        finally {
            session.close();
        }

        return res;
    }

    @Override
    public void update(Faq faq) throws SQLException {
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.update(faq);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public void delete(Faq faq) throws SQLException {
        Session session = null;
        try{
            session = new SessionBuilder().build();
            session.delete(faq);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }
}
