package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MenuController {
	
	@FXML
	private AnchorPane rightAnchor;
	@FXML
	private ImageView imagemMenu, imagemAlunos, imagemProfessores, imagemCursos, imagemTurmas, imagemRelatorios, imagemInicio;
	@FXML
	public void initialize() {
		Image iconeAluno = new Image(getClass().getResourceAsStream("/view/assets/iconAluno.png"));
		Image iconeProfessor = new Image(getClass().getResourceAsStream("/view/assets/iconProfessor.png"));
		Image iconeCurso = new Image(getClass().getResourceAsStream("/view/assets/iconCurso.png"));
		Image iconeTurma = new Image(getClass().getResourceAsStream("/view/assets/iconTurma.png"));
		Image iconeRelatorio = new Image(getClass().getResourceAsStream("/view/assets/iconRelatorio.png"));
		Image iconeMenu = new Image(getClass().getResourceAsStream("/view/assets/iconMenu.png"));
		imagemAlunos.setImage(iconeAluno);
		imagemProfessores.setImage(iconeProfessor);
		imagemCursos.setImage(iconeCurso);
		imagemTurmas.setImage(iconeTurma);
		imagemRelatorios.setImage(iconeRelatorio);
		imagemMenu.setImage(iconeMenu);
	
	}
	
	// métodos que mudam a página do lado direito conforme o botão clicado
	public void setPaginaAlunos() {
		setPagina("/view/TelaAlunos.fxml", rightAnchor);
	}
	
	public void setPaginaCursos() {
		setPagina("/view/TelaCursos.fxml", rightAnchor);
	}
	
	public void setPaginaProfessores() {
		setPagina("/view/TelaProfessores.fxml", rightAnchor);
	}
	
	public void setPaginaTurmas() {
		setPagina("/view/TelaTurmas.fxml", rightAnchor);
	}
	
	public void setPaginaRelatorios() {
		setPagina("/view/TelaRelatorios.fxml", rightAnchor);
	}
	
	
	// método q muda pagina
	public void setPagina(String fxml, AnchorPane anchor) {
		Node node = null;
		try {
			node = (Node)FXMLLoader.load(getClass().getResource(fxml));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		anchor.getChildren().setAll(node);
	}
}
