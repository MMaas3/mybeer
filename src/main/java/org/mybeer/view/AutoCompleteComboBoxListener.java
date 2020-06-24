package org.mybeer.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Function;
import java.util.function.Predicate;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

  private final ComboBox<T> comboBox;
  private final ObservableList<T> data;
  private final Function<String, Predicate<T>> filter;
  private boolean moveCaretToPos = false;
  private int caretPos;

  private AutoCompleteComboBoxListener(final ComboBox<T> comboBox, Function<String, Predicate<T>> filter) {
    this.comboBox = comboBox;
    data = comboBox.getItems();
    this.filter = filter;

    this.comboBox.setEditable(true);
    this.comboBox.setOnKeyPressed(t -> comboBox.hide());
    this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
  }

  static <T> void addAutoCompleteListener(ComboBox<T> comboBox, Function<String, Predicate<T>> filter) {
    new AutoCompleteComboBoxListener<>(comboBox, filter);
  }

  @Override
  public void handle(KeyEvent event) {

    if (event.getCode() == KeyCode.UP) {
      caretPos = -1;
      moveCaret(comboBox.getEditor().getText().length());
      return;
    } else if (event.getCode() == KeyCode.DOWN) {
      if (!comboBox.isShowing()) {
        comboBox.show();
      }
      caretPos = -1;
      moveCaret(comboBox.getEditor().getText().length());
      return;
    } else if (event.getCode() == KeyCode.BACK_SPACE) {
      moveCaretToPos = true;
      caretPos = comboBox.getEditor().getCaretPosition();
    } else if (event.getCode() == KeyCode.DELETE) {
      moveCaretToPos = true;
      caretPos = comboBox.getEditor().getCaretPosition();
    }

    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
        || event.isControlDown() || event.getCode() == KeyCode.HOME
        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
      return;
    }

    ObservableList<T> list = FXCollections.observableArrayList();

    data.stream().filter(filter.apply(this.comboBox.getEditor().getText())).forEach(list::add);

    String t = comboBox.getEditor().getText();

    comboBox.setItems(list);
    comboBox.getEditor().setText(t);
    if (!moveCaretToPos) {
      caretPos = -1;
    }
    moveCaret(t.length());
    if (!list.isEmpty()) {
      comboBox.show();
    }
  }

  private void moveCaret(int textLength) {
    if (caretPos == -1) {
      comboBox.getEditor().positionCaret(textLength);
    } else {
      comboBox.getEditor().positionCaret(caretPos);
    }
    moveCaretToPos = false;
  }

}
