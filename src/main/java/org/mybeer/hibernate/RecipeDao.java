package org.mybeer.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mybeer.model.recipe.Recipe;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.stream.Stream;

public class RecipeDao extends GenericDao<Recipe> {
  public RecipeDao() {
    super(Recipe.class);
  }

  public Optional<Recipe> getByOldId(int id, Session session) {
    final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

    final CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
    final Root<Recipe> root = criteriaQuery.from(Recipe.class);
    criteriaQuery.where(criteriaBuilder.equal(root.get("oldId"), id));

    final Query<Recipe> query = session.createQuery(criteriaQuery);
    return query.getResultStream().findAny();
  }

  public Optional<Recipe> getById(Long id, Session session) {
    return Optional.ofNullable(session.get(Recipe.class, id));
  }
}
