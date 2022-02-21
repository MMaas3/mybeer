package org.mybeer.view;


import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import org.mybeer.hibernate.GenericDao;
import org.mybeer.hibernate.SessionFactorySingleton;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OverviewTableViewUtils {
  public static <T> void fillTableWithAllOfType(Class<T> type, TableView<T> tableView,
                                                Comparator<T> comparator) {
    try (final Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final GenericDao<T> genericDao = new GenericDao<>(type);
      final List<T> entities = genericDao.getAll(session).sorted(comparator).collect(Collectors.toList());
      tableView.setItems(FXCollections.observableArrayList(entities));
    }
  }
}
