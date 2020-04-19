package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.ingredient.Fermentable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class FermentableDao extends GenericDao<Fermentable> {

  public Optional<Fermentable> findByName(String name, Session session) {
    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

    final CriteriaQuery<Fermentable> criteriaQuery = criteriaBuilder.createQuery(Fermentable.class);
    final Root<Fermentable> root = criteriaQuery.from(Fermentable.class);
    criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

    final Query<Fermentable> query = session.createQuery(criteriaQuery);
    return query.getResultStream().findAny();
  }
}
