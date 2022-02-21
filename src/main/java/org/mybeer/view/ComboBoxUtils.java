package org.mybeer.view;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.hibernate.Session;
import org.mybeer.hibernate.GenericDao;
import org.mybeer.hibernate.SessionFactorySingleton;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mybeer.view.AutoCompleteComboBoxListener.addAutoCompleteListener;

public class ComboBoxUtils {

  public static <T> void setUpComboBoxForEntity(Class<T> type, ComboBox<T> comboBox, Function<T, String> nameSupplier) {
    try (Session session = SessionFactorySingleton.getSessionFactory().openSession()) {
      final List<T> values = new GenericDao<T>(type).getAll(session).collect(Collectors.toList());
      comboBox.setItems(FXCollections.observableArrayList(values));
      comboBox.setConverter(new StringConverter<>() {
        @Override
        public String toString(T object) {
          return object == null ? "" : nameSupplier.apply(object);
        }

        @Override
        public T fromString(String string) {
          return values.stream().filter(object -> nameSupplier.apply(object).equals(string)).findAny().orElseThrow();
        }
      });
      addAutoCompleteListener(comboBox,
          text -> object -> nameSupplier.apply(object).toLowerCase().contains(text.toLowerCase()));
    }
  }

  public static <T> void setUpComboBoxForEnum(Class<T> type, ComboBox<T> comboBox, Function<T, String> nameSupplier) {
    final List<T> values = List.of(type.getEnumConstants());
    comboBox.setItems(FXCollections.observableArrayList(values));
    comboBox.setConverter(new StringConverter<>() {
      @Override
      public String toString(T object) {
        return object == null ? "" : nameSupplier.apply(object);
      }

      @Override
      public T fromString(String string) {
        return values.stream().filter(object -> nameSupplier.apply(object).equals(string)).findAny().orElseThrow();
      }
    });
    addAutoCompleteListener(comboBox,
        text -> object -> nameSupplier.apply(object).toLowerCase().contains(text.toLowerCase()));
  }
}
