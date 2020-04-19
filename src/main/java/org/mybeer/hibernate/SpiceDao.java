package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.ingredient.Spice;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class SpiceDao extends GenericDao<Spice> {
  public Optional<Spice> findByName(String name, Session session) {
    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

    final CriteriaQuery<Spice> criteriaQuery = criteriaBuilder.createQuery(Spice.class);
    final Root<Spice> root = criteriaQuery.from(Spice.class);
    criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

    final Query<Spice> query = session.createQuery(criteriaQuery);
    return query.getResultStream().findAny();
  }
}
