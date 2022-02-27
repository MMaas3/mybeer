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
import org.mybeer.model.ingredient.Spice;

import org.mybeer.hibernate.GenericDao;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class SpiceOverviewController {
  @FXML
  private TableView<Spice> tableView;
  @FXML
  private Button openButton;
  @FXML
  private Button newButton;
  @FXML
  private Button cloneButton;
  @FXML
  public void initialize() {
    OverviewTableViewUtils.fillTableWithAllOfType(Spice.class, tableView, Comparator.comparing(Spice::getName));
    openButton.setOnAction(event -> {
      final ObservableList<Spice> selectedItems = tableView.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      final Long id = selectedItems.get(0).getId();
      try {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/SpiceEditor.fxml"));
        final SpiceEditorController editorController = new SpiceEditorController(id);
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
        loader.setLocation(getClass().getClassLoader().getResource("view/SpiceEditor.fxml"));
      final SpiceEditorController editorController = new SpiceEditorController();
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    cloneButton.setOnAction(event -> {
      final ObservableList<Spice> selectedItems = tableView.getSelectionModel().getSelectedItems();
      if (selectedItems.isEmpty()) {
        return;
      }
      final Node source = (Node) event.getSource();
      final Stage stage = (Stage) source.getScene().getWindow();
      final Long id = selectedItems.get(0).getId();
      try {
        final GenericDao<Spice> dao = new GenericDao<>(Spice.class);
        final Spice clone = (Spice) BeanUtils.cloneBean(dao.getById(id).get());
        clone.setName(clone.getName()+ " Copy");
        clone.setId(null);
        final Spice save = dao.save(clone);
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/SpiceEditor.fxml"));
        final SpiceEditorController editorController = new SpiceEditorController(save.getId());
        loader.setController(editorController);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
      } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
  }
}