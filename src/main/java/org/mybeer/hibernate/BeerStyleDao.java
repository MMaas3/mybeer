package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.BeerStyle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class BeerStyleDao extends GenericDao<BeerStyle> {
  public Optional<BeerStyle> findByName(String name) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

      final CriteriaQuery<BeerStyle> criteriaQuery = criteriaBuilder.createQuery(BeerStyle.class);
      final Root<BeerStyle> root = criteriaQuery.from(BeerStyle.class);
      criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

      final Query<BeerStyle> query = session.createQuery(criteriaQuery);
      return query.getResultStream().findAny();
    }
  }
}
