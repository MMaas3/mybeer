package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class GenericDao<T> {

  public T save(T objectToSave) {
    try (Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      Transaction transaction = session.beginTransaction();
      T result = this.save(session, objectToSave);

      session.flush();
      transaction.commit();

      return result;
    }
  }

  public T save(Session session, T objectToSave) {
    boolean valid = true;
    for (Method method : objectToSave.getClass().getMethods()) {
      if (method.getName().equals("validate")) {
        try {
          Method validateMethod = objectToSave.getClass().getMethod("validate");
          Object result = validateMethod.invoke(objectToSave);
          valid = (boolean) result;

        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
          e.printStackTrace();
        }
        break;
      }
    }
    if (!valid) {
      System.err.println("validation of object failed");
    }
    session.saveOrUpdate(objectToSave);

    return objectToSave;
  }

}
