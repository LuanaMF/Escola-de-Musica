package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador para o menu principal da aplicação.
 * É responsável por gerenciar as ações dos botões do menu e alterar o conteúdo exibido na área direita da tela.
 */
public class MenuController {
	
    @FXML
    private AnchorPane rightAnchor;
    @FXML
    private ImageView imagemMenu, imagemAlunos, imagemProfessores, imagemCursos, imagemTurmas, imagemRelatorios, imagemInicio;

    /**
     * Inicializa o controlador do menu.
     * Carrega e define os ícones para os botões do menu.
     */
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

    /**
     * Define a página de alunos no painel direito.
     */
    public void setPaginaAlunos() {
        setPagina("/view/TelaAlunos.fxml", rightAnchor);
    }

    /**
     * Define a página de cursos no painel direito.
     */
    public void setPaginaCursos() {
        setPagina("/view/TelaCursos.fxml", rightAnchor);
    }

    /**
     * Define a página de professores no painel direito.
     */
    public void setPaginaProfessores() {
        setPagina("/view/TelaProfessores.fxml", rightAnchor);
    }

    /**
     * Define a página de turmas no painel direito.
     */
    public void setPaginaTurmas() {
        setPagina("/view/TelaTurmas.fxml", rightAnchor);
    }

    /**
     * Define a página de relatórios no painel direito.
     */
    public void setPaginaRelatorios() {
        setPagina("/view/TelaRelatorios.fxml", rightAnchor);
    }

    /**
     * Carrega e define uma nova página no painel especificado.
     * 
     * @param fxml O caminho do arquivo FXML a ser carregado.
     * @param anchor O painel onde a nova página será exibida.
     */
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
