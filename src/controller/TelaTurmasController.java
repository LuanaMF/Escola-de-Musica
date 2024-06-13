package controller;

import javafx.collections.ObservableList;
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
import model.Turma;

/**
 * Controlador para a tela de gerenciamento de turmas.
 */
public class TelaTurmasController {

    @FXML
    private VBox form;
    @FXML
    private Button formBotao;
    @FXML
    private TableView<Turma> tabelaTurmas;
    @FXML
    private TableColumn<Turma,String> colunaCodigo;
    @FXML
    private TableColumn<Turma,Curso> colunaCurso;
    @FXML
    private TableColumn<Turma,String> colunaDiasDaSemana;
    @FXML
    private TableColumn<Turma,String> colunaHorario;
    @FXML
    private TextField campoCodigo;
    @FXML
    private TextField campoCurso;
    @FXML
    private TextField campoDiasDaSemana;
    @FXML
    private TextField campoHorario;
    @FXML
    private Label mensagem;

    private Turma turmaSelecionado; // Turma selecionada na tabela

    /**
     * Método executado ao inicializar o controlador.
     */
    @FXML
    public void initialize() {
        preencheTabela(); // Preenche a tabela com as turmas existentes
        disableForm(); // Desabilita o formulário
        clickEvent(); // Configura o evento de clique na tabela
    }

    /**
     * Ação do botão "Adicionar".
     */
    public void botaoAdicionarAction() {
        clean(); // Limpa os campos do formulário
        formBotao.setText("Adicionar"); // Define o texto do botão como "Adicionar"
        enableForm(); // Habilita o formulário para adição
    }

    /**
     * Ação do botão "Editar".
     */
    public void botaoEditarAction() {
        mensagem.setText(""); // Limpa a mensagem de erro/sucesso
        if(turmaSelecionado != null) { // Verifica se uma turma está selecionada
            formBotao.setText("Atualizar"); // Define o texto do botão como "Atualizar"
            atribuiValores(); // Atribui os valores da turma selecionada aos campos do formulário
            enableForm(); // Habilita o formulário para edição
        }
        else {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho
            mensagem.setText("Selecione uma turma para atualizá-la"); // Define a mensagem de erro
        }
    }

    /**
     * Ação do botão "Excluir".
     */
    public void botaoExcluirAction() {
        mensagem.setText(""); // Limpa a mensagem de erro/sucesso
        disableForm(); // Desabilita o formulário
        if(turmaSelecionado != null) { // Verifica se uma turma está selecionada
            Turma.delete(turmaSelecionado); // Exclui a turma selecionada
        }
        else {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho
            mensagem.setText("Selecione uma turma para excluí-la"); // Define a mensagem de erro
        }
    }

    /**
     * Ação do botão do formulário (Adicionar/Atualizar).
     */
    public void formBotaoAction() {
        mensagem.setText(""); // Limpa a mensagem de erro/sucesso
        String texto = formBotao.getText(); // Obtém o texto do botão

        boolean tudoCerto = verificaInformacoes(); // Verifica se todas as informações foram preenchidas corretamente
        int index = findCurso(); // Busca o curso especificado nos registros

        if(index != -1) { // Se o curso for encontrado
            Curso curso = EscolaDeMusicaDB.getInstance().getCursos().get(index); // Obtém o curso da lista
            if(tudoCerto) {
                if(texto.equals("Adicionar")) {
                    createTurma(curso); // Cria uma nova turma
                    mensagem.setTextFill(Color.GREEN); // Define a cor do texto da mensagem como verde
                    mensagem.setText("Turma adicionada com sucesso!"); // Define a mensagem de sucesso
                    disableForm(); // Desabilita o formulário após adicionar
                }
                else if(texto.equals("Atualizar")) {
                    editTurma(curso); // Atualiza a turma existente
                    mensagem.setTextFill(Color.GREEN); // Define a cor do texto da mensagem como verde
                    mensagem.setText("Turma atualizada com sucesso!"); // Define a mensagem de sucesso
                    disableForm(); // Desabilita o formulário após atualizar
                }
            }
        }
        else {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho
            mensagem.setText("Curso não encontrado! Verifique o nome e tente novamente"); // Define a mensagem de erro
        }
    }

    /**
     * Limpa os campos do formulário.
     */
    private void clean() {
        campoCodigo.setText(""); // Limpa o campo de código
        campoCurso.setText(""); // Limpa o campo de curso
        campoHorario.setText(""); // Limpa o campo de horário
        campoDiasDaSemana.setText(""); // Limpa o campo de dias da semana
    }

    /**
     * Atribui os valores da turma selecionada aos campos do formulário.
     */
    private void atribuiValores(){
        campoCodigo.setText(turmaSelecionado.getCodigo()); // Define o código da turma no campo correspondente
        campoCurso.setText(turmaSelecionado.getCurso()); // Define o curso da turma no campo correspondente
        campoHorario.setText(turmaSelecionado.getHorario()); // Define o horário da turma no campo correspondente
        campoDiasDaSemana.setText(turmaSelecionado.getDiasDaSemana()); // Define os dias da semana da turma no campo correspondente
    }

    /**
     * Desabilita o formulário.
     */
    private void disableForm() {       
        form.setVisible(false); // Torna o formulário invisível
        form.setDisable(true); // Desabilita o formulário
        formBotao.setVisible(false); // Torna o botão do formulário invisível
        formBotao.setDisable(true); // Desabilita o botão do formulário
    }

    /**
     * Habilita o formulário.
     */
    private void enableForm() {
        form.setVisible(true); // Torna o formulário visível
        form.setDisable(false); // Habilita o formulário
        formBotao.setVisible(true); // Torna o botão do formulário visível
        formBotao.setDisable(false); // Habilita o botão do formulário
    }

    /**
     * Preenche a tabela com as turmas existentes.
     */
    private void preencheTabela(){       
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo")); // Define a fábrica de valores para a coluna de código
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso")); // Define a fábrica de valores para a coluna de curso
        colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario")); // Define a fábrica de valores para a coluna de horário
        colunaDiasDaSemana.setCellValueFactory(new PropertyValueFactory<>("diasDaSemana")); // Define a fábrica de valores para a coluna de dias da semana
        tabelaTurmas.setItems(EscolaDeMusicaDB.getInstance().getTurmas()); // Define os itens da tabela com a lista de turmas do banco de dados
    }

    /**
     * Cria uma nova turma.
     * 
     * @param curso Curso associado à turma.
     */
    private void createTurma(Curso curso){
        Turma.create(campoCodigo.getText(), curso, campoDiasDaSemana.getText(), campoHorario.getText()); // Cria uma nova turma com os dados do formulário
    }

    /**
     * Edita uma turma existente.
     * 
     * @param curso Curso associado à turma.
     */
    private void editTurma(Curso curso) {
        Turma.edit(turmaSelecionado, campoCodigo.getText(), curso, campoDiasDaSemana.getText(), campoHorario.getText()); // Edita a turma selecionada com os dados do formulário
        int index = tabelaTurmas.getItems().indexOf(turmaSelecionado); // Obtém o índice da turma na tabela
        tabelaTurmas.getItems().set(index, turmaSelecionado); // Atualiza a turma na tabela
    }
    /**
     * Busca o curso especificado nos registros.
     * 
     * @return Índice do curso na lista, ou -1 se não encontrado.
     */
    private int findCurso() {
        int index = -1; // Inicializa o índice como -1
        ObservableList<Curso> lista = EscolaDeMusicaDB.getInstance().getCursos(); // Obtém a lista de cursos do banco de dados
        String nome = campoCurso.getText(); // Obtém o nome do curso do campo de texto
        for(Curso curso: lista) { // Itera sobre a lista de cursos
            if(curso.getNome().equalsIgnoreCase(nome)) { // Verifica se o nome do curso na lista é igual ao nome especificado (ignorando maiúsculas e minúsculas)
                index = lista.indexOf(curso); // Obtém o índice do curso na lista
            }
        }
        return index; // Retorna o índice encontrado ou -1 se o curso não foi encontrado
    }

    /**
     * Verifica se todas as informações necessárias foram preenchidas no formulário.
     * 
     * @return true se todas as informações estiverem preenchidas corretamente, caso contrário false.
     */
    private boolean verificaInformacoes() {
        mensagem.setText(""); // Limpa a mensagem de erro/sucesso
        boolean codigo = !campoCodigo.getText().isEmpty() && !campoCodigo.getText().isBlank(); // Verifica se o campo de código não está vazio ou em branco
        boolean horario = !campoHorario.getText().isEmpty() && !campoHorario.getText().isBlank(); // Verifica se o campo de horário não está vazio ou em branco
        boolean curso = !campoCurso.getText().isEmpty() && !campoCurso.getText().isBlank(); // Verifica se o campo de curso não está vazio ou em branco
        boolean duasDaSemana = !campoDiasDaSemana.getText().isEmpty() && !campoDiasDaSemana.getText().isBlank(); // Verifica se o campo de dias da semana não está vazio ou em branco
        boolean tudoCerto = codigo && horario && curso && duasDaSemana; // Verifica se todas as informações estão preenchidas corretamente

        if(!tudoCerto) {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho
            mensagem.setText("Preencha todos os campos para prosseguir"); // Define a mensagem de erro
        }

        return tudoCerto; // Retorna true se todas as informações estiverem preenchidas corretamente, caso contrário false
    }

    /**
     * Configura o evento de clique na tabela de turmas.
     */
    private void clickEvent() {
        tabelaTurmas.setOnMouseClicked(new EventHandler<MouseEvent>() {             
            @Override
            public void handle(MouseEvent click) {
                turmaSelecionado = tabelaTurmas.getSelectionModel().getSelectedItem(); // Obtém a turma selecionada na tabela
            }
        });
    }
}
