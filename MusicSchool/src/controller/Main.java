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


public class Main extends Application {
	
	private static Stage palco;
	@Override
	public void start(Stage primaryStage) throws IOException {
	
		palco = primaryStage;
		palco.setResizable(false);
		
		setTela("/view/TelaInicial.fxml");
		palco.show();			
		
	}
	
	//Método para mudar tela
	public void setTela(String fxml) throws IOException {
		Parent r = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(r,750,600);
		String css = this.getClass().getResource("/view/cssFile.css").toExternalForm();
		scene.getStylesheets().add(css);
		palco.setScene(scene);
	}
	
	public static void main(String[] args) {
		Aluno.create("Luana Fraga", "19211188", "12345678901");
		Curso.create("Violão", "30h");
		Turma.create("VIO01", EscolaDeMusicaDB.getInstance().getCursos().get(0), "Segunda-feira e Quarta-feira", "15:00");
		Professor.create("Luana Oliveira", "12345678912");
		launch(args);
	}
}
