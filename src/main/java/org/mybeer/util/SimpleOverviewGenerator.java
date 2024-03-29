package org.mybeer.util;

import org.mybeer.hibernate.GenericDao;
import org.mybeer.model.BrewingSystem;
import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.ingredient.Spice;
import org.mybeer.model.ingredient.Yeast;
import org.mybeer.model.recipe.Recipe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;

public class SimpleOverviewGenerator {
  public static void main(String[] args) throws Exception {
    createOverview(Fermentable.class, "name", "getName", false);
    createOverview(Hop.class, "name", "getName", false);
    createOverview(Yeast.class, "name", "getName", false);
    createOverview(Spice.class, "name", "getName", false);
    createOverview(Recipe.class, "name", "getId", true);
    createOverview(BrewingSystem.class, "name", "getName", false);
  }

  private static void createOverview(Class<?> type, String identityProperty, String identityPropertyGetter,
                                     boolean reverseOrder) throws Exception {
    createFxml(type, identityProperty);
    createController(type, identityPropertyGetter, reverseOrder);
  }

  private static void createController(Class<?> type, String identityPropertyGetter, boolean reverseOrder)
      throws Exception {
    final StringBuilder stringBuilder = new StringBuilder("package org.mybeer.view;\n\n");
    stringBuilder.append("import javafx.collections.ObservableList;\n");
    stringBuilder.append("import javafx.fxml.FXML;\n");
    stringBuilder.append("import javafx.fxml.FXMLLoader;\n");
    stringBuilder.append("import javafx.scene.Node;\n");
    stringBuilder.append("import javafx.scene.Parent;\n");
    stringBuilder.append("import javafx.scene.Scene;\n");
    stringBuilder.append("import javafx.scene.control.TableView;\n");
    stringBuilder.append("import javafx.scene.control.Button;\n");
    stringBuilder.append("import javafx.stage.Stage;\n");
    stringBuilder.append("import org.apache.commons.beanutils.BeanUtils;\n");
    stringBuilder.append("import ").append(type.getName()).append(";\n\n");
    stringBuilder.append("import ").append(GenericDao.class.getName()).append(";\n\n");
    stringBuilder.append("import java.io.IOException;\n\n");
    stringBuilder.append("import java.lang.reflect.InvocationTargetException;\n");
    stringBuilder.append("import java.util.Comparator;\n\n");


    final String controllerName = getControllerName(type);
    stringBuilder.append("public class ").append(controllerName).append(" {\n");
    stringBuilder.append("  @FXML\n");
    final String simpleName = type.getSimpleName();
    stringBuilder.append("  private TableView<").append(simpleName).append("> tableView;\n");
    stringBuilder.append("  @FXML\n");
    stringBuilder.append("  private Button openButton;\n");
    stringBuilder.append("  @FXML\n");
    stringBuilder.append("  private Button newButton;\n");
    stringBuilder.append("  @FXML\n");
    stringBuilder.append("  private Button cloneButton;\n");
    stringBuilder.append("  @FXML\n");
    stringBuilder.append("  public void initialize() {\n");
    stringBuilder.append("    OverviewTableViewUtils.fillTableWithAllOfType(").append(simpleName)
                 .append(".class, tableView, Comparator.comparing(").append(simpleName).append("::")
                 .append(identityPropertyGetter).append(")").append(reverseOrder ? ".reversed()" : "").append(");\n");
    stringBuilder.append("    openButton.setOnAction(event -> {\n");
    stringBuilder.append("      final ObservableList<").append(simpleName)
                 .append("> selectedItems = tableView.getSelectionModel().getSelectedItems();\n");
    stringBuilder.append("      if (selectedItems.isEmpty()) {\n");
    stringBuilder.append("        return;\n");
    stringBuilder.append("      }\n");
    stringBuilder.append("      final Node source = (Node) event.getSource();\n");
    stringBuilder.append("      final Stage stage = (Stage) source.getScene().getWindow();\n");
    stringBuilder.append("      final Long id = selectedItems.get(0).getId();\n");
    stringBuilder.append("      try {\n");
    stringBuilder.append("        final FXMLLoader loader = new FXMLLoader();\n");
    stringBuilder.append("        loader.setLocation(getClass().getClassLoader().getResource(\"view/")
                 .append(simpleName).append("Editor.fxml\"));\n");
    stringBuilder.append("        final ").append(simpleName).append("EditorController editorController = new ")
                 .append(simpleName).append("EditorController(id);\n");
    stringBuilder.append("        loader.setController(editorController);\n");
    stringBuilder.append("        final Parent root = loader.load();\n");
    stringBuilder.append("        stage.setScene(new Scene(root));\n");
    stringBuilder.append("      } catch (IOException e) {\n");
    stringBuilder.append("        e.printStackTrace();\n");
    stringBuilder.append("      }\n");
    stringBuilder.append("    });\n\n");

    stringBuilder.append("    newButton.setOnAction(event -> {\n");
    stringBuilder.append("      final Node source = (Node) event.getSource();\n");
    stringBuilder.append("      final Stage stage = (Stage) source.getScene().getWindow();\n");
    stringBuilder.append("      try {\n");
    stringBuilder.append("        final FXMLLoader loader = new FXMLLoader();\n");
    stringBuilder.append("        loader.setLocation(getClass().getClassLoader().getResource(\"view/")
                 .append(simpleName).append("Editor.fxml\"));\n");
    stringBuilder.append("        final ").append(simpleName).append("EditorController editorController = new ")
                 .append(simpleName).append("EditorController();\n");
    stringBuilder.append("        loader.setController(editorController);\n");
    stringBuilder.append("        final Parent root = loader.load();\n");
    stringBuilder.append("        stage.setScene(new Scene(root));\n");
    stringBuilder.append("      } catch (IOException e) {\n");
    stringBuilder.append("        e.printStackTrace();\n");
    stringBuilder.append("      }\n");
    stringBuilder.append("    });\n\n");

    stringBuilder.append("    cloneButton.setOnAction(event -> {\n");
    stringBuilder.append("      final ObservableList<").append(simpleName)
                 .append("> selectedItems = tableView.getSelectionModel().getSelectedItems();\n");
    stringBuilder.append("      if (selectedItems.isEmpty()) {\n");
    stringBuilder.append("        return;\n");
    stringBuilder.append("      }\n");
    stringBuilder.append("      final Node source = (Node) event.getSource();\n");
    stringBuilder.append("      final Stage stage = (Stage) source.getScene().getWindow();\n");
    stringBuilder.append("      final Long id = selectedItems.get(0).getId();\n");
    stringBuilder.append("      try {\n");

    stringBuilder.append("        final GenericDao<").append(simpleName).append("> dao = new GenericDao<>(")
                 .append(simpleName).append(".class);\n");
    stringBuilder.append("        final ").append(simpleName).append(" clone = (").append(simpleName)
                 .append(") BeanUtils.cloneBean(dao.getById(id).get());\n");
    stringBuilder.append("        clone.setName(clone.getName()+ \" Copy\");\n");
    stringBuilder.append("        clone.setId(null);\n");
    stringBuilder.append("        final ").append(simpleName).append(" save = dao.save(clone);\n");
    stringBuilder.append("        final FXMLLoader loader = new FXMLLoader();\n");
    stringBuilder.append("        loader.setLocation(getClass().getClassLoader().getResource(\"view/")
                 .append(simpleName).append("Editor.fxml\"));\n");
    stringBuilder.append("        final ").append(simpleName).append("EditorController editorController = new ")
                 .append(simpleName).append("EditorController(save.getId());\n");
    stringBuilder.append("        loader.setController(editorController);\n");
    stringBuilder.append("        final Parent root = loader.load();\n");
    stringBuilder.append("        stage.setScene(new Scene(root));\n");
    stringBuilder.append(
        "      } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | " +
            "IllegalAccessException e) {\n");
    stringBuilder.append("        e.printStackTrace();\n");
    stringBuilder.append("      }\n");
    stringBuilder.append("    });\n");

    stringBuilder.append("  }\n");
    stringBuilder.append("}");

    final File file = new File("src/main/java/org/mybeer/view/" + controllerName + ".java");
    System.out.println(file.getAbsolutePath());
    try (final FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.append(stringBuilder.toString());
    }


    System.out.println(stringBuilder);
  }


  private static void createFxml(Class<?> type, String identityProperty) throws Exception {
    final DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
    final Document document = documentBuilder.newDocument();
    document.appendChild(document.createProcessingInstruction("import", "javafx.collections.FXCollections"));
    document
        .appendChild(document.createProcessingInstruction("import", "javafx.scene.control.cell.PropertyValueFactory"));
    document.appendChild(document.createProcessingInstruction("import", "javafx.scene.control.TableColumn"));
    document.appendChild(document.createProcessingInstruction("import", "javafx.scene.control.TableView"));
    document.appendChild(document.createProcessingInstruction("import", "javafx.scene.layout.BorderPane"));
    document.appendChild(document.createProcessingInstruction("import", "javafx.scene.layout.VBox"));
    document.appendChild(document.createProcessingInstruction("import", "javafx.scene.control.Button"));

    final Element root = document.createElement("BorderPane");
    root.setAttribute("prefHeight", "1000");
    root.setAttribute("prefWidth", "1000");
    root.setAttribute("xmlns", "http://javafx.com/javafx/10.0.2-internal");
    root.setAttribute("xmlns:fx", "http://javafx.com/fxml/1");
    root.setAttribute("fx:controller", "org.mybeer.view." + getControllerName(type));
    root.setAttribute("style", "-fx-padding: 10 10 10 10;");
    document.appendChild(root);

    final Element right = document.createElement("right");
    root.appendChild(right);
    final Element buttons = document.createElement("VBox");
    buttons.setAttribute("spacing", "10");
    buttons.setAttribute("style", "-fx-padding: 0 0 0 10;");
    right.appendChild(buttons);

    final Element openButton = document.createElement("Button");
    openButton.setAttribute("fx:id", "openButton");
    openButton.setAttribute("text", "Open " + type.getSimpleName());
    buttons.appendChild(openButton);

    final Element newButton = document.createElement("Button");
    newButton.setAttribute("fx:id", "newButton");
    newButton.setAttribute("text", "New " + type.getSimpleName());
    buttons.appendChild(newButton);

    final Element cloneButton = document.createElement("Button");
    cloneButton.setAttribute("fx:id", "cloneButton");
    cloneButton.setAttribute("text", "Clone " + type.getSimpleName());
    buttons.appendChild(cloneButton);

    final Element deleteButton = document.createElement("Button");
    deleteButton.setAttribute("fx:id", "deleteButton");
    deleteButton.setAttribute("text", "Delete " + type.getSimpleName());
    buttons.appendChild(deleteButton);

    final Element center = document.createElement("center");
    root.appendChild(center);

    final Element tableView = document.createElement("TableView");
    tableView.setAttribute("fx:id", "tableView");
    tableView.setAttribute("layoutX", "14");
    tableView.setAttribute("layoutY", "30");
    tableView.setAttribute("prefWidth", "500");
    center.appendChild(tableView);

    final Element columns = document.createElement("columns");
    tableView.appendChild(columns);
    final Element tableColumn = document.createElement("TableColumn");
    columns.appendChild(tableColumn);
    final Element cellValueFactory = document.createElement("cellValueFactory");
    tableColumn.appendChild(cellValueFactory);
    final Element propertyValueFactory = document.createElement("PropertyValueFactory");
    propertyValueFactory.setAttribute("property", identityProperty);
    cellValueFactory.appendChild(propertyValueFactory);


    final Element items = document.createElement("items");
    tableView.appendChild(items);
    final Element fxCollection = document.createElement("FXCollections");
    fxCollection.setAttribute("fx:factory", "observableArrayList");
    items.appendChild(fxCollection);


    // final StringWriter writer = new StringWriter();
    final File file = new File("src/main/resources/view/" + type.getSimpleName() + "Overview.fxml");
    System.out.println(file.getAbsolutePath());
    final FileWriter writer = new FileWriter(file);
    final TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
    final Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.transform(new DOMSource(document), new StreamResult(writer));

    // System.out.println(writer.toString());

  }

  private static String getControllerName(Class<?> type) {
    return type.getSimpleName() + "OverviewController";
  }

}
