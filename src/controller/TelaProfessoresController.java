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
import model.EscolaDeMusicaDB;
import model.Professor;

/**
 * Controlador para a tela de gestão de professores da aplicação de Escola de Música.
 * Gerencia adição, edição, exclusão e visualização de professores.
 */
public class TelaProfessoresController {

    // Componentes FXML
    @FXML
    private VBox form;
    @FXML
    private Button formBotao;
    @FXML
    private TableView<Professor> tabelaProfessores;
    @FXML
    private TableColumn<Professor,String> colunaNome;
    @FXML
    private TableColumn<Professor,String> colunaCpf;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCpf;
    @FXML
    private Label mensagem;
    
    private Professor professorSelecionado; // Professor selecionado na tabela
    
    /**
     * Método inicializador que é chamado quando o FXML é carregado.
     * Configura a tabela de professores, desabilita o formulário e configura o evento de clique na tabela.
     */
    @FXML
    public void initialize() {
        preencheTabela();
        disableForm();       
        clickEvent();
    }
    
    /**
     * Ação do botão "Adicionar". Limpa os campos do formulário e habilita o formulário para adicionar um novo professor.
     */
    public void botaoAdicionarAction() {
        clean();
        formBotao.setText("Adicionar");
        enableForm();
    }
    
    /**
     * Ação do botão "Editar". Verifica se um professor está selecionado e habilita o formulário para editar seus dados.
     * Caso nenhum professor esteja selecionado, exibe mensagem de erro.
     */
    public void botaoEditarAction() {
        mensagem.setText("");
        if (professorSelecionado != null) {
            formBotao.setText("Atualizar");
            atribuiValores();
            enableForm();
        } else {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Selecione um(a) professor(a) para atualizá-lo(a)");
        }
    }
    
    /**
     * Ação do botão "Excluir". Verifica se um professor está selecionado e exclui o professor.
     * Caso nenhum professor esteja selecionado, exibe mensagem de erro.
     */
    public void botaoExcluirAction() {
        mensagem.setText("");
        if (professorSelecionado != null) {
            Professor.delete(professorSelecionado);
        } else {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Selecione um(a) aluno(a) para exclui-lo(a)");
        }
    }
    
    /**
     * Ação do botão do formulário. Executa ação de adicionar ou atualizar professor, dependendo do estado do botão.
     * Exibe mensagens de sucesso ou erro, dependendo da operação.
     */
    public void formBotaoAction() {
        mensagem.setText("");
        String texto = formBotao.getText();
        
        boolean tudoCerto = verificaInformacoes();
        
        if (tudoCerto) {
            if (texto.equals("Adicionar")) {
                createProfessor();
                mensagem.setTextFill(Color.GREEN);
                mensagem.setText("Professor(a) adicionado(a) com sucesso!");
                disableForm();
            } else if (texto.equals("Atualizar")) {
                editProfessor();
                mensagem.setTextFill(Color.GREEN);
                mensagem.setText("Professor(a) atualizado(a) com sucesso!");
                disableForm();
            }
        }
    }
    
    /**
     * Preenche os campos do formulário com os valores do professor selecionado para edição.
     */
    private void atribuiValores() {
        campoNome.setText(professorSelecionado.getNome());
        campoCpf.setText(professorSelecionado.getCpf());
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
     * Preenche a tabela de professores com os dados do banco de dados.
     * Configura as colunas da tabela e define os itens da tabela.
     */
    private void preencheTabela() {        
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tabelaProfessores.setItems(EscolaDeMusicaDB.getInstance().getProfessores());
    }
    
    /**
     * Cria um novo professor com base nos dados preenchidos no formulário e adiciona ao banco de dados.
     */
    private void createProfessor() {
        Professor.create(campoNome.getText(), campoCpf.getText());
    }
    
    /**
     * Edita os dados do professor selecionado com base nos novos dados preenchidos no formulário.
     * Atualiza o professor no banco de dados e na tabela.
     */
    private void editProfessor() {
        Professor.edit(professorSelecionado, campoNome.getText(), campoCpf.getText());
        int index = tabelaProfessores.getItems().indexOf(professorSelecionado);
        tabelaProfessores.getItems().set(index, professorSelecionado);
    }
    
    /**
     * Verifica se todos os campos obrigatórios do formulário estão preenchidos.
     * Exibe mensagem de erro se algum campo estiver vazio.
     * @return true se todos os campos estiverem preenchidos corretamente, false caso contrário.
     */
    private boolean verificaInformacoes() {
        mensagem.setText("");
        boolean cpf = !campoCpf.getText().isEmpty() && !campoCpf.getText().isBlank();
        boolean nome = !campoNome.getText().isEmpty() && !campoNome.getText().isBlank();
        boolean tudoCerto = cpf && nome;
        
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
        campoCpf.setText("");
    }
    
    /**
     * Configura o evento de clique na tabela de professores.
     * Seleciona o professor clicado na tabela e atualiza o professor selecionado.
     */
    private void clickEvent() {
        tabelaProfessores.setOnMouseClicked(new EventHandler<MouseEvent>() {             
            @Override
            public void handle(MouseEvent click) {
                professorSelecionado = tabelaProfessores.getSelectionModel().getSelectedItem();                                                
            }
        });
    }
}
