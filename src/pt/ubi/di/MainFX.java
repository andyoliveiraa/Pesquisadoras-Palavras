package pt.ubi.di;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        Tab textTab = new Tab("Bloco de Texto", createTextPane());
        Tab colorTab = new Tab("Escolha de Cor", createColorPane());
        textTab.setClosable(false);
        colorTab.setClosable(false);
        tabPane.getTabs().addAll(textTab, colorTab);

        Scene scene = new Scene(tabPane, 500, 400);
        primaryStage.setTitle("Ficha JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createTextPane() {
        BorderPane borderPane = new BorderPane();
        TextArea textArea = new TextArea();
        borderPane.setCenter(textArea);
        
        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(10));
        
        Label label = new Label("Palavra:");
        TextField wordField = new TextField();
        Button countButton = new Button("Contar");
        Label resultLabel = new Label("Ocorrências: 0");
        CheckBox caseInsensitive = new CheckBox("Ignorar Maiúsculas");

        countButton.setOnAction(e -> {
            String text = textArea.getText();
            String word = wordField.getText();
            boolean ignoreCase = caseInsensitive.isSelected();
            int count = countOccurrences(text, word, ignoreCase);
            resultLabel.setText("Ocorrências: " + count);
        });

        flowPane.getChildren().addAll(label, wordField, countButton, resultLabel, caseInsensitive);
        borderPane.setBottom(flowPane);
        
        return borderPane;
    }

    private int countOccurrences(String text, String word, boolean ignoreCase) {
        if (text.isEmpty() || word.isEmpty()) return 0;
        if (ignoreCase) {
            text = text.toLowerCase();
            word = word.toLowerCase();
        }
        String[] words = text.split("\\s+");
        int count = 0;
        for (String w : words) {
            if (w.equals(word)) count++;
        }
        return count;
    }

    private BorderPane createColorPane() {
        BorderPane borderPane = new BorderPane();
        Pane colorPane = new Pane();
        colorPane.setPrefSize(300, 200);
        borderPane.setCenter(colorPane);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Slider redSlider = createSlider();
        Slider greenSlider = createSlider();
        Slider blueSlider = createSlider();
        Label redLabel = new Label("0");
        Label greenLabel = new Label("0");
        Label blueLabel = new Label("0");
        
        redSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateColor(colorPane, redSlider, greenSlider, blueSlider, redLabel, greenLabel, blueLabel));
        greenSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateColor(colorPane, redSlider, greenSlider, blueSlider, redLabel, greenLabel, blueLabel));
        blueSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateColor(colorPane, redSlider, greenSlider, blueSlider, redLabel, greenLabel, blueLabel));

        gridPane.addRow(0, new Label("Red"), redSlider, redLabel);
        gridPane.addRow(1, new Label("Green"), greenSlider, greenLabel);
        gridPane.addRow(2, new Label("Blue"), blueSlider, blueLabel);
        
        borderPane.setBottom(gridPane);
        return borderPane;
    }

    private Slider createSlider() {
        Slider slider = new Slider(0, 255, 0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(50);
        slider.setBlockIncrement(10);
        return slider;
    }

    private void updateColor(Pane pane, Slider red, Slider green, Slider blue, Label rLabel, Label gLabel, Label bLabel) {
        int r = (int) red.getValue();
        int g = (int) green.getValue();
        int b = (int) blue.getValue();
        rLabel.setText(String.valueOf(r));
        gLabel.setText(String.valueOf(g));
        bLabel.setText(String.valueOf(b));
        pane.setStyle("-fx-background-color: rgb(" + r + "," + g + "," + b + ");");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
