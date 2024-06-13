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
import model.Aluno;
import model.EscolaDeMusicaDB;

/**
 * Controlador para a tela de gestão de alunos da aplicação de Escola de Música.
 * Gerencia adição, edição, exclusão e visualização de alunos.
 */
public class TelaAlunosController {
    
    // Componentes FXML
    @FXML
    private VBox form;
    @FXML
    private Button formBotao;
    @FXML
    private TableView<Aluno> tabelaAlunos;
    @FXML
    private TableColumn<Aluno,String> colunaNome;
    @FXML
    private TableColumn<Aluno,String> colunaCpf;
    @FXML
    private TableColumn<Aluno,String> colunaNumMatricula;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCpf;
    @FXML
    private TextField campoNumMatricula;
    @FXML
    private Label mensagem;
    
    private Aluno alunoSelecionado; // Aluno selecionado na tabela
    
    /**
     * Método inicializador que é chamado quando o FXML é carregado.
     * Configura a tabela de alunos, desabilita o formulário e configura o evento de clique na tabela.
     */
    @FXML
    public void initialize() {
        preencheTabela();
        disableForm();
        clickEvent();
    }
    
    /**
     * Ação do botão "Adicionar". Limpa os campos do formulário e habilita o formulário para adicionar um novo aluno.
     */
    public void botaoAdicionarAction() {
        clean();
        formBotao.setText("Adicionar");
        enableForm();
    }
    
    /**
     * Ação do botão "Editar". Verifica se um aluno está selecionado e habilita o formulário para editar seus dados.
     * Caso nenhum aluno esteja selecionado, exibe mensagem de erro.
     */
    public void botaoEditarAction() {
        mensagem.setText("");
        if (alunoSelecionado != null) {
            formBotao.setText("Atualizar");
            atribuiValores();
            enableForm();
        } else {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Selecione um aluno para atualizá-lo");
        }
    }
    
    /**
     * Ação do botão "Excluir". Verifica se um aluno está selecionado e exclui o aluno.
     * Caso nenhum aluno esteja selecionado, exibe mensagem de erro.
     */
    public void botaoExcluirAction() {
        mensagem.setText("");
        if (alunoSelecionado != null) {
            Aluno.delete(alunoSelecionado);
        } else {
            mensagem.setTextFill(Color.RED);
            mensagem.setText("Selecione um aluno para exclui-lo");
        }
    }
    
    /**
     * Ação do botão do formulário. Executa ação de adicionar ou atualizar aluno, dependendo do estado do botão.
     * Exibe mensagens de sucesso ou erro, dependendo da operação.
     */
    public void formBotaoAction() {
        mensagem.setText("");
        String texto = formBotao.getText();
        
        boolean tudoCerto = verificaInformacoes();
        
        if (tudoCerto) {
            if (texto.equals("Adicionar")) {
                createAluno();
                mensagem.setTextFill(Color.GREEN);
                mensagem.setText("Aluno adicionado com sucesso!");
                disableForm();
            } else if (texto.equals("Atualizar")) {
                editAluno();
                mensagem.setTextFill(Color.GREEN);
                mensagem.setText("Aluno atualizado com sucesso!");
                disableForm();
            }
        }
    }
    
    /**
     * Preenche os campos do formulário com os valores do aluno selecionado para edição.
     */
    private void atribuiValores() {
        campoNome.setText(alunoSelecionado.getNome());
        campoCpf.setText(alunoSelecionado.getCpf());
        campoNumMatricula.setText(alunoSelecionado.getNumeroDeMatricula());
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
     * Preenche a tabela de alunos com os dados do banco de dados.
     * Configura as colunas da tabela e define os itens da tabela.
     */
    private void preencheTabela() {        
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaNumMatricula.setCellValueFactory(new PropertyValueFactory<>("numeroDeMatricula"));
        tabelaAlunos.setItems(EscolaDeMusicaDB.getInstance().getAlunos());
    }
    
    /**
     * Cria um novo aluno com base nos dados preenchidos no formulário e adiciona ao banco de dados.
     */
    private void createAluno() {
        Aluno.create(campoNome.getText(), campoNumMatricula.getText(), campoCpf.getText());
    }
    
    /**
     * Edita os dados do aluno selecionado com base nos novos dados preenchidos no formulário.
     * Atualiza o aluno no banco de dados e na tabela.
     */
    private void editAluno() {
        Aluno.edit(alunoSelecionado, campoNome.getText(), campoNumMatricula.getText(), campoCpf.getText());
        int index = tabelaAlunos.getItems().indexOf(alunoSelecionado);
        tabelaAlunos.getItems().set(index, alunoSelecionado);
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
        boolean numMatricula = !campoNumMatricula.getText().isEmpty() && !campoNumMatricula.getText().isBlank();
        boolean tudoCerto = cpf && nome && numMatricula;
        
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
        campoCpf.setText("");
        campoNome.setText("");
        campoNumMatricula.setText("");
    }
    
    /**
     * Configura o evento de clique na tabela de alunos.
     * Seleciona o aluno clicado na tabela e atualiza o aluno selecionado.
     */
    private void clickEvent() {
        tabelaAlunos.setOnMouseClicked(new EventHandler<MouseEvent>() {             
            @Override
            public void handle(MouseEvent click) {
                alunoSelecionado = tabelaAlunos.getSelectionModel().getSelectedItem();                                               
            }
        });
    }
}
