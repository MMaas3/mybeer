package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.ingredient.HopForm;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class HopDao extends GenericDao<Hop> {
  public HopDao() {
    super(Hop.class);
  }

  public Optional<Hop> findByNameAndForm(String name, HopForm hopForm, Session session) {
    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

    final CriteriaQuery<Hop> criteriaQuery = criteriaBuilder.createQuery(Hop.class);
    final Root<Hop> root = criteriaQuery.from(Hop.class);
    criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name))
                 .where(criteriaBuilder.equal(root.get("form"), hopForm));


    final Query<Hop> query = session.createQuery(criteriaQuery);
    return query.getResultStream().findAny();
  }
}
