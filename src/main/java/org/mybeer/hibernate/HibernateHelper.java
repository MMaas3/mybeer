package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class HibernateHelper {
    public static void main(String[] args) throws IOException{
        HibernateHelper helper = new HibernateHelper();
        helper.testApp();
    }

    public void testApp() throws IOException{
        Session session = SessionFactorySingleton.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        transaction.commit();
        session.close();


        SessionFactorySingleton.closeSessionFactory();
    }
}
