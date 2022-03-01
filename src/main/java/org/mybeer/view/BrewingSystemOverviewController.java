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
import org.mybeer.model.BrewingSystem;

import org.mybeer.hibernate.GenericDao;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class BrewingSystemOverviewController {
  @FXML
  private TableView<BrewingSystem> tableView;
  @FXML
  private Button openButton;
  @FXML
  private Button newButton;
  @FXML
  private Button cloneButton;
  @FXML
  public void initialize() {
    OverviewTableViewUtils.fillTableWithAllOfType(BrewingSystem.class, tableView, Comparator.comparing(BrewingSystem::getName));
    openButton.setOnAction(event -> {
      final ObservableList<BrewingSystem> selectedItems = tableView.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      final Long id = selectedItems.get(0).getId();
      try {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/BrewingSystemEditor.fxml"));
        final BrewingSystemEditorController editorController = new BrewingSystemEditorController(id);
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
        loader.setLocation(getClass().getClassLoader().getResource("view/BrewingSystemEditor.fxml"));
        final BrewingSystemEditorController editorController = new BrewingSystemEditorController();
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    cloneButton.setOnAction(event -> {
      final ObservableList<BrewingSystem> selectedItems = tableView.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      final Long id = selectedItems.get(0).getId();
      try {
        final GenericDao<BrewingSystem> dao = new GenericDao<>(BrewingSystem.class);
        final BrewingSystem clone = (BrewingSystem) BeanUtils.cloneBean(dao.getById(id).get());
        clone.setName(clone.getName()+ " Copy");
        clone.setId(null);
        final BrewingSystem save = dao.save(clone);
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/BrewingSystemEditor.fxml"));
        final BrewingSystemEditorController editorController = new BrewingSystemEditorController(save.getId());
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
  }
}