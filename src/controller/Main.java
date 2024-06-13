package controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Aluno;
import model.Curso;
import model.EscolaDeMusicaDB;
import model.Professor;
import model.Turma;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe principal da aplicação que extende a classe Application do JavaFX.
 * É responsável por iniciar a aplicação e carregar a tela inicial.
 */
public class Main extends Application {

    private static Stage palco;

    /**
     * Método de início da aplicação.
     * 
     * @param primaryStage O palco principal da aplicação.
     * @throws IOException Se houver um erro ao carregar o arquivo FXML.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        palco = primaryStage;
        palco.setResizable(false);
        
        setTela("/view/TelaInicial.fxml");
        palco.show();
    }

    /**
     * Método para trocar a tela exibida.
     * 
     * @param fxml O caminho do arquivo FXML a ser carregado.
     * @throws IOException Se houver um erro ao carregar o arquivo FXML.
     */
    public void setTela(String fxml) throws IOException {
        Parent r = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(r, 750, 600);
        String css = this.getClass().getResource("/view/cssFile.css").toExternalForm();
        scene.getStylesheets().add(css);
        palco.setScene(scene);
    }

    /**
     * Método principal que inicia a aplicação.
     * 
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        Aluno.create("Luana Fraga", "19211188", "12345678901");
        Curso.create("Violão", "30h");
        Turma.create("VIO01", EscolaDeMusicaDB.getInstance().getCursos().get(0), "Segunda-feira e Quarta-feira", "15:00");
        Professor.create("Luana Oliveira", "12345678912");
        launch(args);
    }
}
