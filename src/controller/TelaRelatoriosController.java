package controller;

import java.util.Arrays;

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
import model.Aluno;
import model.Curso;
import model.EscolaDeMusicaDB;
import model.Professor;
import model.Turma;

/**
 * Controller responsável por gerenciar a tela de relatórios da escola de música.
 */
public class TelaRelatoriosController {

    // Elementos FXML - Essas anotações @FXML são usadas para injetar elementos da interface do usuário (FXML) neste controlador.

    @FXML
    private Label indentificacao; // Label para exibir informações de identificação (por exemplo, título da visualização).
    @FXML
    private TextField textField; // Campo de texto para entrada de dados (por exemplo, número de matrícula, código da turma).
    @FXML
    private Label labelInput; // Label associada ao textField para fornecer instruções ou informações.
    @FXML
    private TextField textField1; // Outro campo de texto para entrada de dados, usado separadamente do textField.
    @FXML
    private Label labelInput1; // Outra label associada ao textField1 para fornecer instruções ou informações.
    @FXML
    private TableView tabela; // TableView para exibir dados em forma de tabela.
    @FXML
    private VBox visualizar; // VBox para agrupar elementos relacionados à visualização de dados.
    @FXML
    private VBox formVisualizar; // VBox para agrupar elementos relacionados ao formulário de visualização.
    @FXML
    private VBox formAdicionar; // VBox para agrupar elementos relacionados ao formulário de adição.
    @FXML
    private Button botaoVisualizar; // Botão para iniciar ação de visualização.
    @FXML
    private Button botaoAdicionar, botaoAdicionarTabela, botaoExcluirTabela; // Botões para adicionar e excluir itens da tabela.
    @FXML
    private Label mensagem; // Label para exibir mensagens de status ou de erro.

    // Variáveis de controle - Variáveis utilizadas para armazenar objetos temporários durante a interação com a interface.

    private Object objetoSendoVisualizado; // Objeto sendo visualizado na interface (por exemplo, uma turma, um professor).
    private Object objetoSelecionado; // Objeto selecionado na tabela (por exemplo, um aluno selecionado para excluir).

    /**
     * Método chamado ao inicializar o controller.
     * Configura os eventos e desabilita elementos iniciais.
     */
    @FXML
    public void initialize() {
        clickEvent(); // Configura o evento de clique na tabela.
        disableAll(); // Desabilita todos os elementos visuais na inicialização.
    }

    // Métodos para ações dos botões

    /**
     * Método para visualizar informações com base na ação escolhida.
     */
    public void visualizar() {
        String acao = labelInput1.getText(); // Obtém o texto da labelInput1 para determinar a ação.
        if (acao.equals("Digite o código da turma: ")) {
            visualizarAlunosDeUmaTurma(); // Chama o método para visualizar alunos de uma turma.
        } else if (acao.equals("Digite o CPF do(a) Professor(a): ")) {
            visualizarTurmasDeUmProfessor(); // Chama o método para visualizar turmas de um professor.
        } else if (acao.equals("Digite o nome do curso: ")) {
            visualizarTurmasDeUmCurso(); // Chama o método para visualizar turmas de um curso.
        }
    }

    /**
     * Configura ação de adicionar com base no botão selecionado.
     */
    public void configAdicionar() {
        String qualAdicionar = botaoAdicionarTabela.getText(); // Obtém o texto do botão para determinar a ação.
        if (qualAdicionar.equals("Adicionar aluno")) {
            configAdicionarAluno(); // Configura a interface para adicionar aluno.
        } else if (qualAdicionar.equals("Adicionar turma p")) {
            configAdicionarTurmaP(); // Configura a interface para adicionar turma padrão a um professor.
        } else if (qualAdicionar.equals("Adicionar turma c")) {
            configAdicionarTurmaC(); // Configura a interface para adicionar turma padrão a um curso.
        }
    }

    /**
     * Adiciona um item com base no botão selecionado.
     */
    public void adicionar() {
        String qualAdicionar = botaoAdicionarTabela.getText(); // Obtém o texto do botão para determinar a ação.
        if (qualAdicionar.equals("Adicionar aluno")) {
            adicionarAluno(); // Adiciona um aluno à turma selecionada.
        } else if (qualAdicionar.equals("Adicionar turma p")) {
            adicionarTurmaP(); // Adiciona uma turma padrão a um professor.
        } else if (qualAdicionar.equals("Adicionar turma c")) {
            adicionarTurmaC(); // Adiciona uma turma padrão a um curso.
        }
    }

    /**
     * Exclui um item com base no botão selecionado.
     */
    public void excluir() {
        String qualAdicionar = botaoAdicionarTabela.getText(); // Obtém o texto do botão para determinar a ação.
        if (qualAdicionar.equals("Adicionar aluno")) {
            excluirAluno(); // Exclui o aluno selecionado da turma.
        } else if (qualAdicionar.equals("Adicionar turma p")) {
            excluirTurmaP(); // Exclui a turma padrão do professor.
        } else if (qualAdicionar.equals("Adicionar turma c")) {
            excluirTurmaC(); // Exclui a turma padrão do curso.
        }
    }

    // Métodos relacionados ao relatório de alunos de uma turma

    /**
     * Configura a interface para visualizar alunos de uma turma.
     */
    public void configVisualizarAlunosDeUmaTurma() {
        disableAll(); // Desabilita todos os elementos visuais.
        clean(); // Limpa os campos de texto.
        enableFormVisualizar(); // Habilita o formulário de visualização.
        labelInput1.setText("Digite o código da turma: "); // Define o texto da labelInput1.
        botaoAdicionarTabela.setText("Adicionar aluno"); // Define o texto do botaoAdicionarTabela.
        botaoExcluirTabela.setText("Excluir aluno"); // Define o texto do botaoExcluirTabela.
    }

    /**
     * Visualiza os alunos de uma turma com base no código informado.
     */
    public void visualizarAlunosDeUmaTurma() {
        if (!textField1.getText().isEmpty() && !textField1.getText().isBlank()) {
            int index = findTurma(textField1.getText()); // Encontra o índice da turma com o código informado.
            if (index != -1) {
                objetoSendoVisualizado = EscolaDeMusicaDB.getInstance().getTurmas().get(index); // Obtém a turma a ser visualizada.
                preencheTabelaAlunos(((Turma) objetoSendoVisualizado).getAlunos()); // Preenche a tabela com os alunos da turma.
                indentificacao.setText("Alunos da turma " + textField1.getText()); // Define o texto da label de identificação.
                disableFormVisualizar(); // Desabilita o formulário de visualização.
                enableVisualizar(); // Habilita a visualização dos elementos.
            } else {
                mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                mensagem.setText("Turma não encontrada! Verifique o código e tente novamente"); // Define o texto da mensagem de erro.
            }
        } else {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
            mensagem.setText("Digite o código da turma para visualizar seus alunos"); // Define o texto da mensagem de erro.
        }
    }

    /**
     * Configura a interface para adicionar aluno a uma turma.
     */
    public void configAdicionarAluno() {
        clean(); // Limpa os campos de texto.
        if (tabela.getItems().size() >= 10) { // Verifica se a tabela já possui 10 alunos.
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
            mensagem.setText("Limite de 10 alunos atingido!"); // Define o texto da mensagem de erro.
        } else {
            labelInput.setText("Digite o número de matrícula do aluno: "); // Define o texto da labelInput.
            enableFormAdicionar(); // Habilita o formulário de adição.
        }
    }

    /**
     * Adiciona um aluno à turma com base no número de matrícula informado.
     */
    public void adicionarAluno() {
        if (!textField.getText().isEmpty() && !textField.getText().isBlank()) {
            int index = findAluno(textField.getText()); // Encontra o índice do aluno com o número de matrícula informado.
            if (index != -1) {
                Aluno aluno = EscolaDeMusicaDB.getInstance().getAlunos().get(index); // Obtém o aluno a ser adicionado.
                Turma turma = (Turma) objetoSendoVisualizado; // Obtém a turma na qual o aluno será adicionado.
                turma.getAlunos().add(aluno); // Adiciona o aluno à turma.
                mensagem.setTextFill(Color.GREEN); // Define a cor do texto da mensagem como verde.
                mensagem.setText("Aluno adicionado com sucesso!"); // Define o texto da mensagem de sucesso.
                disableFormAdicionar(); // Desabilita o formulário de adição.
            } else {
                mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                mensagem.setText("Aluno não encontrado! Verifique o número de matrícula e tente novamente"); // Define o texto da mensagem de erro.
            }
        }
    }

    /**
     * Exclui o aluno selecionado da turma.
     */
    public void excluirAluno() {
        Turma turma = (Turma) objetoSendoVisualizado; // Obtém a turma da qual o aluno será excluído.
        Aluno aluno = (Aluno) objetoSelecionado; // Obtém o aluno selecionado na tabela.
        turma.getAlunos().remove(aluno); // Remove o aluno da turma.
    }

    // Métodos relacionados ao relatório de turmas de um professor

    /**
     * Configura a interface para visualizar as turmas de um professor.
     */
    public void configVisualizarTurmasDeUmProfessor() {
        disableAll(); // Desabilita todos os elementos visuais.
        clean(); // Limpa os campos de texto.
        enableFormVisualizar(); // Habilita o formulário de visualização.
        botaoAdicionarTabela.setText("Adicionar turma p"); // Define o texto do botão para adicionar turma a um professor.
        botaoExcluirTabela.setText("Excluir turma p"); // Define o texto do botão para excluir turma de um professor.
        labelInput1.setText("Digite o CPF do(a) Professor(a): "); // Define o texto da labelInput1.
    }

    /**
     * Visualiza as turmas de um professor com base no CPF informado.
     */
    public void visualizarTurmasDeUmProfessor() {
        if (!textField1.getText().isEmpty() && !textField1.getText().isBlank()) {
            int index = findProfessor(textField1.getText()); // Encontra o índice do professor com o CPF informado.
            if (index != -1) {
                objetoSendoVisualizado = EscolaDeMusicaDB.getInstance().getProfessores().get(index); // Obtém o professor a ser visualizado.
                Professor prof = (Professor) objetoSendoVisualizado; // Converte o objeto para Professor.
                preencheTabelaTurma(prof.getTurmas()); // Preenche a tabela com as turmas do professor.
                indentificacao.setText("Turmas do professor " + textField1.getText()); // Define o texto da label de identificação.
                disableFormVisualizar(); // Desabilita o formulário de visualização.
                enableVisualizar(); // Habilita a visualização dos elementos.
            } else {
                mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                mensagem.setText("Professor não encontrado! Verifique o CPF e tente novamente"); // Define o texto da mensagem de erro.
            }
        } else {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
            mensagem.setText("Digite o CPF do professor para visualizar suas turmas"); // Define o texto da mensagem de erro.
        }
    }

    /**
     * Configura a interface para adicionar uma turma padrão a um professor.
     */
    public void configAdicionarTurmaP() {
        clean(); // Limpa os campos de texto.
        labelInput.setText("Digite o código da turma: "); // Define o texto da labelInput.
        enableFormAdicionar(); // Habilita o formulário de adição.
    }

    /**
     * Adiciona uma turma padrão ao professor com base no código informado.
     */
    public void adicionarTurmaP() {
        if (!textField.getText().isEmpty() && !textField.getText().isBlank()) {
            int index = findTurma(textField.getText()); // Encontra o índice da turma com o código informado.
            if (index != -1) {
                boolean temProf = turmaJaTemProfessor(textField.getText()); // Verifica se a turma já tem um professor.
                if (!temProf) {
                    Turma turma = EscolaDeMusicaDB.getInstance().getTurmas().get(index); // Obtém a turma a ser adicionada.
                    Professor prof = (Professor) objetoSendoVisualizado; // Obtém o professor ao qual a turma será adicionada.
                    prof.getTurmas().add(turma); // Adiciona a turma ao professor.
                    mensagem.setTextFill(Color.GREEN); // Define a cor do texto da mensagem como verde.
                    mensagem.setText("Turma adicionada com sucesso!"); // Define o texto da mensagem de sucesso.
                    disableFormAdicionar(); // Desabilita o formulário de adição.
                } else {
                    mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                    mensagem.setText("Turma já tem professor!"); // Define o texto da mensagem de erro.
                }
            } else {
                mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                mensagem.setText("Turma não encontrada! Verifique o código e tente novamente"); // Define o texto da mensagem de erro.
            }
        }
    }

    /**
     * Exclui a turma padrão do professor.
     */
    public void excluirTurmaP() {
        Professor prof = (Professor) objetoSendoVisualizado; // Obtém o professor do qual a turma será excluída.
        Turma turma = (Turma) objetoSelecionado; // Obtém a turma selecionada na tabela.
        prof.getTurmas().remove(turma); // Remove a turma do professor.
    }

    // Métodos relacionados ao relatório de turmas de um curso

    /**
     * Configura a interface para visualizar as turmas de um curso.
     */
    public void configVisualizarTurmasDeUmCurso() {
        disableAll(); // Desabilita todos os elementos visuais.
        clean(); // Limpa os campos de texto.
        enableFormVisualizar(); // Habilita o formulário de visualização.
        botaoAdicionarTabela.setText("Adicionar turma c"); // Define o texto do botão para adicionar turma a um curso.
        botaoExcluirTabela.setText("Excluir turma c"); // Define o texto do botão para excluir turma de um curso.
        labelInput1.setText("Digite o nome do curso: "); // Define o texto da labelInput1.
    }

    /**
     * Visualiza as turmas de um curso com base no nome informado.
     */
    public void visualizarTurmasDeUmCurso() {
        if (!textField1.getText().isEmpty() && !textField1.getText().isBlank()) {
            int index = findCurso(textField1.getText()); // Encontra o índice do curso com o nome informado.
            if (index != -1) {
                objetoSendoVisualizado = EscolaDeMusicaDB.getInstance().getCursos().get(index); // Obtém o curso a ser visualizado.
                Curso curso = (Curso) objetoSendoVisualizado; // Converte o objeto para Curso.
                preencheTabelaTurma(curso.getTurmas()); // Preenche a tabela com as turmas do curso.
                indentificacao.setText("Turmas do curso " + textField1.getText()); // Define o texto da label de identificação.
                disableFormVisualizar(); // Desabilita o formulário de visualização.
                enableVisualizar(); // Habilita a visualização dos elementos.
            } else {
                mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                mensagem.setText("Curso não encontrado! Verifique o nome e tente novamente"); // Define o texto da mensagem de erro.
            }
        } else {
            mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
            mensagem.setText("Digite o nome do curso para visualizar suas turmas"); // Define o texto da mensagem de erro.
        }
    }

    /**
     * Configura a interface para adicionar uma turma padrão a um curso.
     */
    public void configAdicionarTurmaC() {
        clean(); // Limpa os campos de texto.
        labelInput.setText("Digite o código da turma: "); // Define o texto da labelInput.
        enableFormAdicionar(); // Habilita o formulário de adição.
    }

    /**
     * Adiciona uma turma padrão ao curso com base no código informado.
     */
    public void adicionarTurmaC() {
        if (!textField.getText().isEmpty() && !textField.getText().isBlank()) {
            int index = findTurma(textField.getText()); // Encontra o índice da turma com o código informado.
            if (index != -1) {
                Turma turma = EscolaDeMusicaDB.getInstance().getTurmas().get(index); // Obtém a turma a ser adicionada.
                Curso curso = (Curso) objetoSendoVisualizado; // Obtém o curso ao qual a turma será adicionada.
                curso.getTurmas().add(turma); // Adiciona a turma ao curso.
                mensagem.setTextFill(Color.GREEN); // Define a cor do texto da mensagem como verde.
                mensagem.setText("Turma adicionada com sucesso!"); // Define o texto da mensagem de sucesso.
                disableFormAdicionar(); // Desabilita o formulário de adição.
            } else {
                mensagem.setTextFill(Color.RED); // Define a cor do texto da mensagem como vermelho.
                mensagem.setText("Turma não encontrada! Verifique o código e tente novamente"); // Define o texto da mensagem de erro.
            }
        }
    }

    /**
     * Exclui a turma padrão do curso.
     */
    public void excluirTurmaC() {
        Curso curso = (Curso) objetoSendoVisualizado; // Obtém o curso do qual a turma será excluída.
        Turma turma = (Turma) objetoSelecionado; // Obtém a turma selecionada na tabela.
        curso.getTurmas().remove(turma); // Remove a turma do curso.
    }

    // Métodos de procura

    /**
     * Encontra o índice do professor na lista com base no CPF informado.
     * 
     * @param cpf CPF do professor a ser procurado.
     * @return O índice do professor na lista, ou -1 se não encontrado.
     */
    private int findProfessor(String cpf) {
        int index = -1; // Inicializa o índice como -1.
        ObservableList<Professor> lista = EscolaDeMusicaDB.getInstance().getProfessores(); // Obtém a lista de professores.
        for (Professor professor : lista) { // Percorre a lista de professores.
            if (professor.getCpf().equalsIgnoreCase(cpf)) { // Verifica se o CPF do professor corresponde ao informado.
                index = lista.indexOf(professor); // Obtém o índice do professor na lista.
                break; // Interrompe o loop ao encontrar o professor.
            }
        }
        return index; // Retorna o índice encontrado (ou -1 se não encontrado).
    }

    /**
     * Encontra o índice da turma na lista com base no código informado.
     * 
     * @param codigo Código da turma a ser procurada.
     * @return O índice da turma na lista, ou -1 se não encontrada.
     */
    private int findTurma(String codigo) {
        int index = -1; // Inicializa o índice como -1.
        ObservableList<Turma> lista = EscolaDeMusicaDB.getInstance().getTurmas(); // Obtém a lista de turmas.
        for (Turma turma : lista) { // Percorre a lista de turmas.
            if (turma.getCodigo().equalsIgnoreCase(codigo)) { // Verifica se o código da turma corresponde ao informado.
                index = lista.indexOf(turma); // Obtém o índice da turma na lista.
                break; // Interrompe o loop ao encontrar a turma.
            }
        }
        return index; // Retorna o índice encontrado (ou -1 se não encontrada).
    }

    /**
     * Encontra o índice do aluno na lista com base no número de matrícula informado.
     * 
     * @param numero Número de matrícula do aluno a ser procurado.
     * @return O índice do aluno na lista, ou -1 se não encontrado.
     */
    private int findAluno(String numero) {
        int index = -1; // Inicializa o índice como -1.
        ObservableList<Aluno> lista = EscolaDeMusicaDB.getInstance().getAlunos(); // Obtém a lista de alunos.
        for (Aluno aluno : lista) { // Percorre a lista de alunos.
            if (aluno.getNumeroDeMatricula().equalsIgnoreCase(numero)) { // Verifica se o número de matrícula corresponde ao informado.
                index = lista.indexOf(aluno); // Obtém o índice do aluno na lista.
                break; // Interrompe o loop ao encontrar o aluno.
            }
        }
        return index; // Retorna o índice encontrado (ou -1 se não encontrado).
    }

    /**
     * Encontra o índice do curso na lista com base no nome informado.
     * 
     * @param nome Nome do curso a ser procurado.
     * @return O índice do curso na lista, ou -1 se não encontrado.
     */
    private int findCurso(String nome) {
        int index = -1; // Inicializa o índice como -1.
        ObservableList<Curso> lista = EscolaDeMusicaDB.getInstance().getCursos(); // Obtém a lista de cursos.
        for (Curso curso : lista) { // Percorre a lista de cursos.
            if (curso.getNome().equalsIgnoreCase(nome)) { // Verifica se o nome do curso corresponde ao informado.
                index = lista.indexOf(curso); // Obtém o índice do curso na lista.
                break; // Interrompe o loop ao encontrar o curso.
            }
        }
        return index; // Retorna o índice encontrado (ou -1 se não encontrado).
    }

    // Métodos auxiliares

    /**
     * Desabilita todos os elementos visuais na interface.
     */
    private void disableAll() {
        formVisualizar.setVisible(false); // Torna o formulário de visualização invisível.
        formVisualizar.setDisable(true); // Desabilita o formulário de visualização.
        formAdicionar.setVisible(false); // Torna o formulário de adição invisível.
        formAdicionar.setDisable(true); // Desabilita o formulário de adição.
        visualizar.setVisible(false); // Torna a visualização invisível.
        visualizar.setDisable(true); // Desabilita a visualização.
        botaoVisualizar.setVisible(false); // Torna o botão de visualização invisível.
        botaoVisualizar.setDisable(true); // Desabilita o botão de visualização.
        botaoAdicionar.setVisible(false); // Torna o botão de adição invisível.
        botaoAdicionar.setDisable(true); // Desabilita o botão de adição.
    }

    /**
     * Habilita o formulário de adição e o botão de adicionar.
     */
    private void enableFormAdicionar() {
        formAdicionar.setVisible(true); // Torna o formulário de adição visível.
        formAdicionar.setDisable(false); // Habilita o formulário de adição.
        botaoAdicionar.setVisible(true); // Torna o botão de adicionar visível.
        botaoAdicionar.setDisable(false); // Habilita o botão de adicionar.
    }

    /**
     * Habilita o formulário de visualização e o botão de visualização.
     */
    private void enableFormVisualizar() {
        formVisualizar.setVisible(true); // Torna o formulário de visualização visível.
        formVisualizar.setDisable(false); // Habilita o formulário de visualização.
        botaoVisualizar.setVisible(true); // Torna o botão de visualização visível.
        botaoVisualizar.setDisable(false); // Habilita o botão de visualização.
    }

    /**
     * Desabilita o formulário de visualização e o botão de visualização.
     */
    private void disableFormVisualizar() {
        formVisualizar.setVisible(false); // Torna o formulário de visualização invisível.
        formVisualizar.setDisable(true); // Desabilita o formulário de visualização.
        botaoVisualizar.setVisible(false); // Torna o botão de visualização invisível.
        botaoVisualizar.setDisable(true); // Desabilita o botão de visualização.
    }

    /**
     * Desabilita o formulário de adição e o botão de adicionar.
     */
    private void disableFormAdicionar() {
        formAdicionar.setVisible(false); // Torna o formulário de adição invisível.
        formAdicionar.setDisable(true); // Desabilita o formulário de adição.
        botaoAdicionar.setVisible(false); // Torna o botão de adicionar invisível.
        botaoAdicionar.setDisable(true); // Desabilita o botão de adicionar.
    }

    /**
     * Habilita a visualização dos elementos.
     */
    private void enableVisualizar() {
        visualizar.setVisible(true); // Torna a visualização visível.
        visualizar.setDisable(false); // Habilita a visualização.
    }

    /**
     * Preenche a tabela com as turmas fornecidas.
     * 
     * @param turmas Lista de turmas a ser exibida na tabela.
     */
    private void preencheTabelaTurma(ObservableList<Turma> turmas) {
        tabela.getColumns().clear(); // Limpa as colunas da tabela.

        // Define as colunas da tabela
        TableColumn<Turma, String> colunaCodigo = new TableColumn<>("Código");
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Turma, String> colunaCurso = new TableColumn<>("Curso");
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

        TableColumn<Turma, String> colunaDiasDaSemana = new TableColumn<>("Dias da Semana");
        colunaDiasDaSemana.setCellValueFactory(new PropertyValueFactory<>("diasDaSemana"));

        TableColumn<Turma, String> colunaHorario = new TableColumn<>("Horário");
        colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        // Adiciona as colunas à tabela
        tabela.getColumns().addAll(Arrays.asList(colunaCodigo, colunaCurso, colunaDiasDaSemana, colunaHorario));

        tabela.setItems(turmas); // Define os itens da tabela com a lista de turmas fornecida.
    }

    /**
     * Preenche a tabela com os alunos fornecidos.
     * 
     * @param alunos Lista de alunos a ser exibida na tabela.
     */
    private void preencheTabelaAlunos(ObservableList<Aluno> alunos) {
        tabela.getColumns().clear(); // Limpa as colunas da tabela.

        // Define as colunas da tabela
        TableColumn<Aluno, String> colunaCpf = new TableColumn<>("CPF");
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Aluno, String> colunaNumDeMatricula = new TableColumn<>("Número de Matrícula");
        colunaNumDeMatricula.setCellValueFactory(new PropertyValueFactory<>("numeroDeMatricula"));

        TableColumn<Aluno, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Adiciona as colunas à tabela
        tabela.getColumns().addAll(Arrays.asList(colunaNome, colunaNumDeMatricula, colunaCpf));

        tabela.setItems(alunos); // Define os itens da tabela com a lista de alunos fornecida.
    }

    /**
     * Configura eventos de clique na tabela.
     */
    private void clickEvent() {
        tabela.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                objetoSelecionado = tabela.getSelectionModel().getSelectedItem(); // Obtém o item selecionado na tabela.
            }
        });
    }

    /**
     * Limpa os campos de texto da interface.
     */
    private void clean() {
        textField.setText(""); // Limpa o campo de texto principal.
        textField1.setText(""); // Limpa o campo de texto secundário.
    }

    /**
     * Verifica se a turma já possui um professor associado.
     * 
     * @param codigo Código da turma a ser verificada.
     * @return true se a turma já tiver um professor associado, false caso contrário.
     */
    private boolean turmaJaTemProfessor(String codigo) {
        boolean temProf = false; // Inicializa como false.
        ObservableList<Professor> lista = EscolaDeMusicaDB.getInstance().getProfessores(); // Obtém a lista de professores.
        for (Professor prof : lista) { // Percorre a lista de professores.
            ObservableList<Turma> turmas = prof.getTurmas(); // Obtém as turmas do professor.
            for (Turma turma : turmas) { // Percorre as turmas do professor.
                if (turma.getCodigo().equalsIgnoreCase(codigo)) { // Verifica se a turma já tem o código informado.
                    temProf = true; // Define como true se encontrar a turma.
                    break; // Interrompe o loop interno.
                }
            }
            if (temProf) { // Se já encontrou a turma, interrompe o loop externo.
                break;
            }
        }
        return temProf; // Retorna true se a turma já tem professor associado, false caso contrário.
    }
}