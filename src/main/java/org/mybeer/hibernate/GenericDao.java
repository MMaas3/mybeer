package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mybeer.model.ingredient.Yeast;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class GenericDao<T> {
  private Class<T> type;

  public GenericDao(Class<T> type) {

    this.type = type;
  }

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

  public Stream<T> getAll(Session session) {
    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

    final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
    final Root<T> root = criteriaQuery.from(type);
    final CriteriaQuery<T> all = criteriaQuery.select(root);

    final Query<T> query = session.createQuery(all);
    return query.getResultStream();
  }
}
