package org.mybeer.util;

import org.mybeer.model.ingredient.Fermentable;
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
    // createFxml(Fermentable.class, "name");
    createController(Fermentable.class, "getName");
  }

  private static void createController(Class<?> type, String identityPropertyGetter) throws Exception {
    final StringBuilder stringBuilder = new StringBuilder("package org.mybeer.view;\n\n");
    stringBuilder.append("import javafx.fxml.FXML;\n");
    stringBuilder.append("import javafx.scene.control.TableView;\n");
    stringBuilder.append("import java.util.Comparator;\n");
    stringBuilder.append("import ").append(type.getName()).append(";\n\n");


    final String controllerName = getControllerName(type);
    stringBuilder.append("public class ").append(controllerName).append(" {\n");
    stringBuilder.append("  @FXML\n");
    final String simpleName = type.getSimpleName();
    stringBuilder.append("  private TableView<").append(simpleName).append("> tableView;\n");
    stringBuilder.append("  public void init() {\n");
    stringBuilder.append("    OverviewTableViewUtils.fillTableWithAllOfType(").append(simpleName)
                 .append(".class, tableView, Comparator.comparing(").append(simpleName).append("::")
                 .append(identityPropertyGetter).append("));\n");
    stringBuilder.append("  }");
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
    document.appendChild(document.createProcessingInstruction("import", "javafx.scene.layout.AnchorPane"));

    final Element anchorPane = document.createElement("AnchorPane");
    anchorPane.setAttribute("prefHeight", "1000");
    anchorPane.setAttribute("prefWidth", "1000");
    anchorPane.setAttribute("xmlns", "http://javafx.com/javafx/10.0.2-internal");
    anchorPane.setAttribute("xmlns:fx", "http://javafx.com/fxml/1");
    anchorPane.setAttribute("fx:controller", "org.mybeer.view." + getControllerName(type));
    document.appendChild(anchorPane);

    final Element tableView = document.createElement("TableView");
    tableView.setAttribute("fx:id", "tableView");
    anchorPane.appendChild(tableView);

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
    fxCollection.setAttribute("fx:factory", "observableList");
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
