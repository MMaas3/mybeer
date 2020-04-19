package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.BrewingSystem;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class BrewingSystemDao extends GenericDao<BrewingSystem> {
  public Optional<BrewingSystem> findByName(String name) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

      final CriteriaQuery<BrewingSystem> criteriaQuery = criteriaBuilder.createQuery(BrewingSystem.class);
      final Root<BrewingSystem> root = criteriaQuery.from(BrewingSystem.class);
      criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

      final Query<BrewingSystem> query = session.createQuery(criteriaQuery);
      return query.getResultStream().findAny();
    }
  }
}
