package pt.ubi.di;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainViewController {

    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Label resultLabel;
    @FXML private Button countButton;
    @FXML private CheckBox caseCheckBox;

    @FXML
    public void initialize() {
        countButton.setOnAction(event -> contarPalavra());
    }

    private void contarPalavra() {
        String texto = textArea.getText().trim();
        String palavra = textField.getText().trim();

        if (palavra.isEmpty()) {
            resultLabel.setText("0");
            return;
        }

        // Definir se a contagem será sensível a maiúsculas/minúsculas
        if (!caseCheckBox.isSelected()) {
            texto = texto.toLowerCase();
            palavra = palavra.toLowerCase();
        }

        // Contar ocorrências
        int contador = contarOcorrencias(texto, palavra);

        // Exibir resultado
        resultLabel.setText(String.valueOf(contador));
    }

    private int contarOcorrencias(String texto, String palavra) {
        int contador = 0;
        int indice = texto.indexOf(palavra);

        while (indice != -1) {
            contador++;
            indice = texto.indexOf(palavra, indice + palavra.length());
        }

        return contador;
    }
}
