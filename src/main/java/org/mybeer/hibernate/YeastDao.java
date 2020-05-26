package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.ingredient.Yeast;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.stream.Stream;

public class YeastDao extends GenericDao<Yeast> {
  public Optional<Yeast> findByName(String name) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

      final CriteriaQuery<Yeast> criteriaQuery = criteriaBuilder.createQuery(Yeast.class);
      final Root<Yeast> root = criteriaQuery.from(Yeast.class);
      criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

      final Query<Yeast> query = session.createQuery(criteriaQuery);
      return query.getResultStream().findAny();
    }
  }

  public Stream<Yeast> getAll(Session session) {
    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

    final CriteriaQuery<Yeast> criteriaQuery = criteriaBuilder.createQuery(Yeast.class);
    final Root<Yeast> root = criteriaQuery.from(Yeast.class);
    final CriteriaQuery<Yeast> all = criteriaQuery.select(root);

    final Query<Yeast> query = session.createQuery(all);
    return query.getResultStream();
  }
}
