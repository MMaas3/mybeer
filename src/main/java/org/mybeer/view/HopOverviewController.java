package org.mybeer.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.commons.beanutils.BeanUtils;
import org.mybeer.model.ingredient.Hop;

import org.mybeer.hibernate.GenericDao;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class HopOverviewController {
  @FXML
  private TableView<Hop> tableView;
  @FXML
  private Button openButton;
  @FXML
  private Button newButton;
  @FXML
  private Button cloneButton;
  @FXML
  public void initialize() {
    OverviewTableViewUtils.fillTableWithAllOfType(Hop.class, tableView, Comparator.comparing(Hop::getName));
    openButton.setOnAction(event -> {
      final ObservableList<Hop> selectedItems = tableView.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      final Long id = selectedItems.get(0).getId();
      try {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/HopEditor.fxml"));
        final HopEditorController editorController = new HopEditorController(id);
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    newButton.setOnAction(event -> {
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      try {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/HopEditor.fxml"));
      final HopEditorController editorController = new HopEditorController();
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    cloneButton.setOnAction(event -> {
      final ObservableList<Hop> selectedItems = tableView.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      final Long id = selectedItems.get(0).getId();
      try {
        final GenericDao<Hop> dao = new GenericDao<>(Hop.class);
        final Hop clone = (Hop) BeanUtils.cloneBean(dao.getById(id).get());
        clone.setName(clone.getName()+ " Copy");
        clone.setId(null);
        final Hop save = dao.save(clone);
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/HopEditor.fxml"));
        final HopEditorController editorController = new HopEditorController(save.getId());
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
  }
}