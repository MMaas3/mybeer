package org.mybeer.util;

import org.mybeer.model.ingredient.Fermentable;
import org.mybeer.model.ingredient.Hop;
import org.mybeer.model.ingredient.Spice;
import org.mybeer.model.ingredient.Yeast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

import javax.persistence.Enumerated;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.List;

public class SimpleEntityEditorGenerator {

  public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
    createEditor(Fermentable.class);
    createEditor(Hop.class);
    createEditor(Yeast.class);
    createEditor(Spice.class);
  }

  private static void createEditor(Class<?> type) throws ParserConfigurationException, IOException, TransformerException {
    createFxml(type);
    createController(type);
  }

  private static void createController(Class<?> type) throws IOException {


    final StringBuilder stringBuilder = new StringBuilder("package org.mybeer.view;\n\n");
    final String simpleName = type.getSimpleName();
    stringBuilder.append("import javafx.fxml.FXML;\n");
    stringBuilder.append("import javafx.fxml.FXMLLoader;\n");
    stringBuilder.append("import javafx.scene.Node;\n");
    stringBuilder.append("import javafx.scene.Parent;\n");
    stringBuilder.append("import javafx.scene.Scene;\n");
    stringBuilder.append("import javafx.scene.control.Button;\n");
    List.of(type.getDeclaredFields()).stream().map(SimpleEntityEditorGenerator::getFieldType).distinct()
        .forEach(fieldType -> stringBuilder.append("import javafx.scene.control.").append(fieldType).append(";\n"));
    stringBuilder.append("import javafx.stage.Stage;\n");
    stringBuilder.append("import javafx.util.converter.BigDecimalStringConverter;\n");
    stringBuilder.append("import javafx.util.converter.NumberStringConverter;\n");
    stringBuilder.append("import ").append(type.getName()).append(";\n");
    stringBuilder.append("import org.mybeer.hibernate.").append(simpleName).append("Dao;\n");
    List.of(type.getDeclaredFields()).stream()
        .filter(field -> !field.getType().isPrimitive())
        .map(field -> field.getType().getName())
        .filter(fieldType -> !fieldType.startsWith("java.lang"))
        .distinct()
        .forEach(fieldType -> stringBuilder.append("import " + fieldType).append(";\n"));
    stringBuilder.append("import java.io.IOException;\n\n");
    final String className = createControllerName(simpleName);
    stringBuilder.append("\n").append("public class ").append(className).append(" {\n");
    stringBuilder.append("  private ").append(simpleName).append(" ");
    appendVarName(stringBuilder, simpleName).append(";")
                                            .append("\n");
    stringBuilder.append("  @FXML\n");
    stringBuilder.append("  private Button backButton;\n");
    stringBuilder.append("  @FXML\n");
    stringBuilder.append("  private Button saveButton;\n");
    for (Field field : type.getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers())) {
        stringBuilder.append("  @FXML\n");
        stringBuilder.append("  private ").append(getFieldTypeForController(field)).append(" ")
                     .append(createFxId(field)).append(";\n");
      }
    }
    stringBuilder.append("\n");

    stringBuilder.append("  public ").append(simpleName).append("EditorController() {\n");
    stringBuilder.append("    this.");
    appendVarName(stringBuilder, simpleName).append(" = ").append("new ").append(simpleName).append("();\n");
    stringBuilder.append("}\n\n");

    stringBuilder.append("  public ").append(simpleName).append("EditorController(Long id) {\n");
    stringBuilder.append("    this.");
    appendVarName(stringBuilder, simpleName).append(" = ").append("new ").append(simpleName)
                                            .append("Dao().getById(id).orElseThrow();\n");
    stringBuilder.append("}\n\n");

    stringBuilder.append("  @FXML").append("\n");
    stringBuilder.append("  public void initialize() {\n");

    stringBuilder.append("    backButton.setOnAction(event -> {\n");
    stringBuilder.append("        final Node source = (Node) event.getSource();\n");
    stringBuilder.append("        final Stage stage = (Stage) source.getScene().getWindow();\n");
    stringBuilder.append("        try {\n");
    stringBuilder.append("          final FXMLLoader loader = new FXMLLoader();\n");
    stringBuilder.append("          loader.setLocation(getClass().getClassLoader().getResource(\"view/")
                 .append(simpleName).append("Overview.fxml\"));\n");
    stringBuilder.append("          final Parent root = loader.load();\n");
    stringBuilder.append("          stage.setScene(new Scene(root));\n");
    stringBuilder.append("        } catch (IOException e) {\n");
    stringBuilder.append("          e.printStackTrace();\n");
    stringBuilder.append("        }\n");
    stringBuilder.append("      });\n");

    stringBuilder.append("    saveButton.setOnAction(event -> new ").append(simpleName).append("Dao().save(");
    appendVarName(stringBuilder, simpleName).append("));\n");

    stringBuilder.append("    final NumberStringConverter numberConverter = new NumberStringConverter();\n");
    stringBuilder
        .append("    final BigDecimalStringConverter bigDecimalConverter = new BigDecimalStringConverter();\n\n");
    for (Field field : type.getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers())) {
        if (getFieldType(field).equals("TextField")) {
          stringBuilder.append("    ").append(createFxId(field)).append(".textProperty().setValue(\"\" + ");
          appendVarName(stringBuilder, simpleName).append(".get").append(field.getName().substring(0, 1).toUpperCase())
                                                  .append(field.getName().substring(1)).append("());\n");
          stringBuilder.append("    ").append(createFxId(field))
                       .append(".textProperty().addListener((observable, oldValue, newValue) -> {\n");
          stringBuilder.append("      ");
          appendVarName(stringBuilder, simpleName).append(".set").append(field.getName().substring(0, 1).toUpperCase())
                                                  .append(field.getName().substring(1));
          if (field.getType().equals(BigDecimal.class)) {
            stringBuilder.append("(bigDecimalConverter.fromString(newValue));\n");

          } else if (field.getType().getSimpleName().equals("int")) {
            stringBuilder.append("(numberConverter.fromString(newValue).intValue());\n");
          } else if (field.getType().equals(Long.class)) {
            stringBuilder.append("(numberConverter.fromString(newValue).longValue());\n");
          } else {
            stringBuilder.append("(newValue);\n");
          }
          stringBuilder.append("    });\n\n");
        } else { //enums
          stringBuilder.append("    ")
                       .append("ComboBoxUtils.setUpComboBoxForEnum(")
                       .append(field.getType().getSimpleName()).append(".class, ")
                       .append(createFxId(field)).append(", ");
          appendVarName(stringBuilder, field.getType().getSimpleName()).append(" -> ");
          appendVarName(stringBuilder, field.getType().getSimpleName()).append(".name()").append(");\n\n");
          stringBuilder.append("    ").append(createFxId(field)).append(".setValue(");
          appendVarName(stringBuilder, simpleName).append(".get").append(field.getName().substring(0, 1).toUpperCase())
                                                  .append(field.getName().substring(1)).append("());\n");
        }

      }
    }


    stringBuilder.append("  }\n");
    stringBuilder.append("}");


    final File file = new File("src/main/java/org/mybeer/view/" + className + ".java");
    System.out.println(file.getAbsolutePath());
    try (final FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.append(stringBuilder.toString());
    }
    System.out.println(stringBuilder);
  }

  private static String getFieldTypeForController(Field field) {
    final String fieldType = getFieldType(field);
    if (fieldType.equals("ComboBox")) {
      return fieldType + "<" + field.getType().getSimpleName() + ">";
    } else {
      return fieldType;
    }
  }

  private static String createControllerName(String simpleName) {
    final String className = simpleName + "EditorController";
    return className;
  }

  private static StringBuilder appendVarName(StringBuilder stringBuilder, String simpleName) {
    return stringBuilder.append(simpleName.substring(0, 1).toLowerCase()).append(simpleName.substring(1));
  }

  private static String createFxId(Field field) {

    return field.getName() + getFieldType(field);
  }

  private static void createFxml(Class<?> type)
      throws ParserConfigurationException, IOException, TransformerException {
    final DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
    final Document document = documentBuilder.newDocument();
    final ProcessingInstruction controlImport =
        document.createProcessingInstruction("import", "javafx.scene.control.*");
    document.appendChild(controlImport);
    final ProcessingInstruction importLayout = document.createProcessingInstruction("import", "javafx.scene.layout.*");
    document.appendChild(importLayout);

    final Element anchorPane = document.createElement("AnchorPane");
    anchorPane.setAttribute("prefHeight", "1000");
    anchorPane.setAttribute("prefWidth", "1000");
    anchorPane.setAttribute("xmlns", "http://javafx.com/javafx/10.0.2-internal");
    anchorPane.setAttribute("xmlns:fx", "http://javafx.com/fxml/1");
    // anchorPane.setAttribute("fx:controller", "org.mybeer.view." + createControllerName(type.getSimpleName()));
    document.appendChild(anchorPane);


    int labelY = 14;
    final Element backButton = document.createElement("Button");
    backButton.setAttribute("fx:id", "backButton");
    backButton.setAttribute("layoutX", "10");
    backButton.setAttribute("layoutY", "" + (labelY - 5));
    backButton.setAttribute("text", "Back");
    anchorPane.appendChild(backButton);

    final Element saveButton = document.createElement("Button");
    saveButton.setAttribute("fx:id", "saveButton");
    saveButton.setAttribute("layoutX", "200");
    saveButton.setAttribute("layoutY", "" + (labelY - 5));
    saveButton.setAttribute("text", "Save");
    anchorPane.appendChild(saveButton);
    labelY += 30;

    for (Field field : type.getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers())) {
        final Element label = document.createElement("Label");
        label.setAttribute("layoutX", "10");
        label.setAttribute("layoutY", "" + labelY);
        label.setAttribute("text", getFormattedName(field));
        anchorPane.appendChild(label);
        addField(field, document, anchorPane, labelY - 5);
        labelY += 30;
      }
    }

    // final StringWriter writer = new StringWriter();
    final File file = new File("src/main/resources/view/" + type.getSimpleName() + "Editor.fxml");
    System.out.println(file.getAbsolutePath());
    final FileWriter writer = new FileWriter(file);
    final TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
    final Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.transform(new DOMSource(document), new StreamResult(writer));

    // System.out.println(writer.toString());
  }

  private static void addField(Field field, Document document, Element anchorPane, int y) {
    final Element element = document.createElement(getFieldType(field));
    element.setAttribute("fx:id", createFxId(field));
    element.setAttribute("layoutX", "200");
    element.setAttribute("layoutY", "" + y);
    element.setAttribute("prefWidth", "200");
    anchorPane.appendChild(element);
  }


  private static String getFieldType(Field field) {
    if (field.getAnnotation(Enumerated.class) != null) {
      return "ComboBox";
    } else {
      return "TextField";
    }
  }

  private static String getFormattedName(Field field) {
    String name = field.getName();
    name = name.substring(0, 1).toUpperCase() + name.substring(1);
    final String[] parts = name.split("(?=\\p{Upper})");
    return String.join(" ", parts);
  }
}
