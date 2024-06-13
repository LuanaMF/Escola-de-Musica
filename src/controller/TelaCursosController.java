package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Curso;
import model.EscolaDeMusicaDB;

/**
 * Controlador para a tela de gestão de cursos da aplicação de Escola de Música.
 * Gerencia adição, edição, exclusão e visualização de cursos.
 */
public class TelaCursosController {
    
    // Componentes FXML
    @FXML
    private VBox form;
    @FXML
    private Button formBotao;
    @FXML
    private TableView<Curso> tabelaCursos;
    @FXML
    private TableColumn<Curso,String> colunaNome;
    @FXML
    private TableColumn<Curso,String> colunaCargaHoraria;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCargaHoraria;
    @FXML
    private Label mensagem;
    
    private Curso cursoSelecionado; // Curso selecionado na tabela
    
    /**
     * Método inicializador que é chamado quando o FXML é carregado.
     * Configura a tabela de cursos, desabilita o formulário e configura o evento de clique na tabela.
     */
    @FXML
    public void initialize() {
        preencheTabela();
        disableForm();
        clickEvent();
    }
    
    /**
     * Ação do botão "Adicionar". Limpa os campos do formulário e habilita o formulário para adicionar um novo curso.
     */
    public void botaoAdicionarAction() {
        clean();
        formBotao.setText("Adicionar");
        enableForm();
    }
    
    /**
     * Ação do botão "Editar". Verifica se um curso está selecionado e habilita o formulário para editar seus dados.
     * Caso nenhum curso esteja selecionado, exibe mensagem de erro.
     */
    public void botaoEditarAction() {
        mensagem.setText("");
        if (cursoSelecionado != null) {
            formBotao.setText("Atualizar");
            atribuiValores();
            enableForm();
        } else {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Selecione um curso para atualizá-lo");
        }
    }
    
    /**
     * Ação do botão "Excluir". Verifica se um curso está selecionado e exclui o curso.
     * Caso nenhum curso esteja selecionado, exibe mensagem de erro.
     */
    public void botaoExcluirAction() {
        mensagem.setText("");
        if (cursoSelecionado != null) {
            Curso.delete(cursoSelecionado);
        } else {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Selecione um curso para exclui-lo");
        }
    }
    
    /**
     * Ação do botão do formulário. Executa ação de adicionar ou atualizar curso, dependendo do estado do botão.
     * Exibe mensagens de sucesso ou erro, dependendo da operação.
     */
    public void formBotaoAction() {
        mensagem.setText("");
        String texto = formBotao.getText();
        
        boolean tudoCerto = verificaInformacoes();
        
        if (tudoCerto) {
            if (texto.equals("Adicionar")) {
                createCurso();
                mensagem.setTextFill(Color.GREEN);
                mensagem.setText("Curso adicionado com sucesso!");
                disableForm();
            } else if (texto.equals("Atualizar")) {
                editCurso();
                mensagem.setTextFill(Color.GREEN);
                mensagem.setText("Curso atualizado com sucesso!");
                disableForm();
            }
        }
    }
    
    /**
     * Preenche os campos do formulário com os valores do curso selecionado para edição.
     */
    private void atribuiValores() {
        campoNome.setText(cursoSelecionado.getNome());
        campoCargaHoraria.setText(cursoSelecionado.getCargaHoraria());
    }
    
    /**
     * Desabilita o formulário (tornando-o invisível e não interativo).
     */
    private void disableForm() {        
        form.setVisible(false);
        form.setDisable(true);
        formBotao.setVisible(false);
        formBotao.setDisable(true);
    }
    
    /**
     * Habilita o formulário (tornando-o visível e interativo).
     */
    private void enableForm() {
        form.setVisible(true);
        form.setDisable(false);
        formBotao.setVisible(true);
        formBotao.setDisable(false);
    }
    
    /**
     * Preenche a tabela de cursos com os dados do banco de dados.
     * Configura as colunas da tabela e define os itens da tabela.
     */
    private void preencheTabela() {        
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        tabelaCursos.setItems(EscolaDeMusicaDB.getInstance().getCursos());
    }
    
    /**
     * Cria um novo curso com base nos dados preenchidos no formulário e adiciona ao banco de dados.
     */
    private void createCurso() {
        Curso.create(campoNome.getText(), campoCargaHoraria.getText());
    }
    
    /**
     * Edita os dados do curso selecionado com base nos novos dados preenchidos no formulário.
     * Atualiza o curso no banco de dados e na tabela.
     */
    private void editCurso() {
        Curso.edit(cursoSelecionado, campoNome.getText(), campoCargaHoraria.getText());
        int index = tabelaCursos.getItems().indexOf(cursoSelecionado);
        tabelaCursos.getItems().set(index, cursoSelecionado);
    }
    
    /**
     * Verifica se todos os campos obrigatórios do formulário estão preenchidos.
     * Exibe mensagem de erro se algum campo estiver vazio.
     * @return true se todos os campos estiverem preenchidos corretamente, false caso contrário.
     */
    private boolean verificaInformacoes() {
        mensagem.setText("");
        boolean nome = !campoNome.getText().isEmpty() && !campoNome.getText().isBlank();
        boolean cargaHoraria = !campoCargaHoraria.getText().isEmpty() && !campoCargaHoraria.getText().isBlank();
        boolean tudoCerto = nome && cargaHoraria;
        
        if (!tudoCerto) {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Preencha todos os campos para prosseguir");
        }
        
        return tudoCerto;
    }
    
    /**
     * Limpa todos os campos do formulário.
     */
    private void clean() {
        campoNome.setText("");
        campoCargaHoraria.setText("");
    }
    
    /**
     * Configura o evento de clique na tabela de cursos.
     * Seleciona o curso clicado na tabela e atualiza o curso selecionado.
     */
    private void clickEvent() {
        tabelaCursos.setOnMouseClicked(new EventHandler<MouseEvent>() {             
            @Override
            public void handle(MouseEvent click) {
                cursoSelecionado = tabelaCursos.getSelectionModel().getSelectedItem();                                               
            }
        });
    }
}