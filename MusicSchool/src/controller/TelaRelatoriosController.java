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

public class TelaRelatoriosController {
	
	@FXML
	private Label indentificacao;
	@FXML
	private TextField textField; //adicionar
	@FXML
	private Label labelInput; //adicionar
	@FXML
	private TextField textField1; //visualizar
	@FXML
	private Label labelInput1; //visualizar
	@FXML
	private TableView tabela;
	@FXML
	private VBox visualizar;
	@FXML
	private VBox formVisualizar;
	@FXML
	private VBox formAdicionar;
	@FXML
	private Button botaoVisualizar;
	@FXML
	private Button botaoAdicionar, botaoAdicionarTabela, botaoExcluirTabela;
	@FXML
	private Label mensagem;
	private Object objetoSendoVisualizado;
	private Object objetoSelecionado;
	
	@FXML
	public void initialize() {
		clickEvent();
		disableAll();
	}
	
	// actions dos botões
	public void visualizar() {
		String acao = labelInput1.getText();
		if(acao.equals("Digite o código da turma: ")) {
			visualizarAlunosDeUmaTurma();
		}
		else if(acao.equals("Digite o CPF do(a) Professor(a): ")) {
			visualizarTurmasDeUmProfessor();
		}
		else if(acao.equals("Digite o nome do curso: ")) {
			visualizarTurmasDeUmCurso();
		}
	}
	
	public void configAdicionar() {
		String qualAdicionar = botaoAdicionarTabela.getText();
		if(qualAdicionar.equals("Adicionar aluno")) {
			configAdicionarAluno();
		}
		else if(qualAdicionar.equals("Adicionar turma p")) {
			configAdicionarTurmaP();
		}
		else if(qualAdicionar.equals("Adicionar turma c")) {
			configAdicionarTurmaC();
		}
	}
	
	public void adicionar() {
		String qualAdicionar = botaoAdicionarTabela.getText();
		if(qualAdicionar.equals("Adicionar aluno")) {
			adicionarAluno();
		}
		else if(qualAdicionar.equals("Adicionar turma p")) {
			adicionarTurmaP();
		}
		else if(qualAdicionar.equals("Adicionar turma c")) {
			adicionarTurmaC();
		}
	}
	public void excluir() {
		String qualAdicionar = botaoAdicionarTabela.getText();
		if(qualAdicionar.equals("Adicionar aluno")) {
			excluirAluno();
		}
		else if(qualAdicionar.equals("Adicionar turma p")) {
			excluirTurmaP();
		}
		else if(qualAdicionar.equals("Adicionar turma c")) {
			excluirTurmaC();
		}
	}
	
	//referentes ao relatorio de alunos de uma turma
 	public void configVisualizarAlunosDeUmaTurma(){
		disableAll();
		clean();
		enableFormVisualizar();
		labelInput1.setText("Digite o código da turma: ");
		botaoAdicionarTabela.setText("Adicionar aluno");
		botaoExcluirTabela.setText("Excluir aluno");
	}
	public void visualizarAlunosDeUmaTurma(){
		
		if(!textField1.getText().isEmpty() && !textField1.getText().isBlank()) {
			int index = findTurma(textField1.getText());
			if(index != -1) {
				objetoSendoVisualizado =  EscolaDeMusicaDB.getInstance().getTurmas().get(index);
				preencheTabelaAlunos(((Turma) objetoSendoVisualizado).getAlunos());
				indentificacao.setText("Alunos da turma " + textField1.getText());
				disableFormVisualizar();
				enableVisualizar();
				
			}
			else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Turma não encontrada! Verifique o código e tente novamente");
			}
		}
		
		else {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Digite o código ta turma para visualizar seus alunos");
		}
	}
	public void configAdicionarAluno() {
		clean();
		if(tabela.getItems().size() >= 10) {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Limite de 10 alunos atingido!");
		}
		else {
			labelInput.setText("Digite o número de matricula do aluno: ");
			enableFormAdicionar();
		}
	}
	public void adicionarAluno() {
		if(!textField.getText().isEmpty() && !textField.getText().isBlank()) {
			int index = findAluno(textField.getText());
			if(index != -1) {
				Aluno aluno = EscolaDeMusicaDB.getInstance().getAlunos().get(index);
				Turma turma = (Turma) objetoSendoVisualizado;
				turma.getAlunos().add(aluno);
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Aluno adicionado com sucesso!");
				disableFormAdicionar();
			}
			else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Aluno não encontrado! Verifique o número de matricula e tente novamente");
			}
		}
	}
 	public void excluirAluno() {
		Turma turma = (Turma) objetoSendoVisualizado;
		Aluno aluno = (Aluno) objetoSelecionado;
		turma.getAlunos().remove(aluno);
	}
	
 	
 	//referentes ao relatorio de turmas de um professor
	public void configVisualizarTurmasDeUmProfessor() {
		disableAll();
		clean();
		enableFormVisualizar();
		botaoAdicionarTabela.setText("Adicionar turma p");
		botaoExcluirTabela.setText("Excluir turma p");
		labelInput1.setText("Digite o CPF do(a) Professor(a): ");
	}
	public void visualizarTurmasDeUmProfessor() {
		if(!textField1.getText().isEmpty() && !textField1.getText().isBlank()) {
			int index = findProfessor(textField1.getText());
			if(index != -1) {
				objetoSendoVisualizado =  EscolaDeMusicaDB.getInstance().getProfessores().get(index);
				Professor prof = (Professor) objetoSendoVisualizado;
				preencheTabelaTurma(prof.getTurmas());
				indentificacao.setText("Turmas do professor " + textField1.getText());
				disableFormVisualizar();
				enableVisualizar();
				
			}
			else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Professor não encontrado! Verifique o cpf e tente novamente");
			}
		}
		
		else {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Digite o cpf do professor para visualizar suas turmas");
		}
	}
	public void configAdicionarTurmaP() {
		clean();
		labelInput.setText("Digite o código da turma: ");
		enableFormAdicionar();
	}
	public void adicionarTurmaP() {
		if(!textField.getText().isEmpty() && !textField.getText().isBlank()) {
			int index = findTurma(textField.getText());
			if(index != -1) {
				boolean temProf = turmaJaTemProfessor(textField.getText());
				if(!temProf) {
					Turma turma = EscolaDeMusicaDB.getInstance().getTurmas().get(index);
					Professor prof = (Professor) objetoSendoVisualizado;
					prof.getTurmas().add(turma);
					mensagem.setTextFill(Color.GREEN);
					mensagem.setText("Turma adicionada com sucesso!");
					disableFormAdicionar();
				}
				else {
					mensagem.setTextFill(Color.RED);
					mensagem.setText("Turma já tem professor!");
				}
			}
			else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Turma não encontrada! Verifique o código e tente novamente");
			}
		}
	}
	public void excluirTurmaP() {
		Professor prof = (Professor) objetoSendoVisualizado;
		Turma turma = (Turma) objetoSelecionado;
		prof.getTurmas().remove(turma);
	}
	
	
	
	//referentes ao relatorio de turmas de um curso
	public void configVisualizarTurmasDeUmCurso() {
		disableAll();
		clean();
		enableFormVisualizar();
		botaoAdicionarTabela.setText("Adicionar turma c");
		botaoExcluirTabela.setText("Excluir turma c");
		labelInput1.setText("Digite o nome do curso: ");
	}
	public void visualizarTurmasDeUmCurso() {
		if(!textField1.getText().isEmpty() && !textField1.getText().isBlank()) {
			int index = findCurso(textField1.getText());
			if(index != -1) {
				objetoSendoVisualizado =  EscolaDeMusicaDB.getInstance().getCursos().get(index);
				Curso curso = (Curso) objetoSendoVisualizado;
				preencheTabelaTurma(curso.getTurmas());
				indentificacao.setText("Turmas do curso " + textField1.getText());
				disableFormVisualizar();
				enableVisualizar();
				
			}
			else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Curso não encontrado! Verifique o nome e tente novamente");
			}
		}
		
		else {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Digite o nome do cursos para visualizar suas turmas");
		}
	}
	public void configAdicionarTurmaC() {
		clean();
		labelInput.setText("Digite o código da turma: ");
		enableFormAdicionar();
	}
	public void adicionarTurmaC() {
		if(!textField.getText().isEmpty() && !textField.getText().isBlank()) {
			int index = findTurma(textField.getText());
			if(index != -1) {
				Turma turma = EscolaDeMusicaDB.getInstance().getTurmas().get(index);
				Curso curso = (Curso) objetoSendoVisualizado;
				curso.getTurmas().add(turma);
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Turma adicionada com sucesso!");
				disableFormAdicionar();
			}
			else {
				mensagem.setTextFill(Color.RED);
				mensagem.setText("Turma não encontrada! Verifique o código e tente novamente");
			}
		}
	}
	public void excluirTurmaC() {
		Curso curso = (Curso) objetoSendoVisualizado;
		Turma turma = (Turma) objetoSelecionado;
		curso.getTurmas().remove(turma);
	}
	
	
	// métodos de procura
	private int findProfessor(String cpf) {
		int index = -1;
		ObservableList<Professor> lista = EscolaDeMusicaDB.getInstance().getProfessores();
		for(Professor professor: lista){
			if(professor.getCpf().equalsIgnoreCase(cpf)) {
				index = lista.indexOf(professor);
			}
		}
		return index;
	}
	private int findTurma(String codigo) {
		int index = -1;
		ObservableList<Turma> lista = EscolaDeMusicaDB.getInstance().getTurmas();
		for(Turma turma: lista){
			if(turma.getCodigo().equalsIgnoreCase(codigo)) {
				index = lista.indexOf(turma);
			}
		}
		return index;
	}
	private int findAluno(String numero) {
		int index = -1;
		ObservableList<Aluno> lista = EscolaDeMusicaDB.getInstance().getAlunos();
		for(Aluno aluno: lista){
			if(aluno.getNumeroDeMatricula().equalsIgnoreCase(numero)) {
				index = lista.indexOf(aluno);
			}
		}
		return index;
	}
	private int findCurso(String nome) {
		int index = -1;
		ObservableList<Curso> lista = EscolaDeMusicaDB.getInstance().getCursos();
		for(Curso curso: lista){
			if(curso.getNome().equalsIgnoreCase(nome)) {
				index = lista.indexOf(curso);
			}
		}
		return index;
	}
	
	
	
	private void disableAll() {
		
		formVisualizar.setVisible(false);
		formVisualizar.setDisable(true);
		formAdicionar.setVisible(false);
		formAdicionar.setDisable(true);
		visualizar.setVisible(false);
		visualizar.setDisable(true);
		botaoVisualizar.setVisible(false);
		botaoVisualizar.setDisable(true);
		botaoAdicionar.setVisible(false);
		botaoAdicionar.setDisable(true);
	}
	
	private void enableFormAdicionar() {
		formAdicionar.setVisible(true);
		formAdicionar.setDisable(false);
		botaoAdicionar.setVisible(true);
		botaoAdicionar.setDisable(false);
	}
	
	private void enableFormVisualizar() {
		formVisualizar.setVisible(true);
		formVisualizar.setDisable(false);
		botaoVisualizar.setVisible(true);
		botaoVisualizar.setDisable(false);
	}
	
	private void disableFormVisualizar() {
		formVisualizar.setVisible(false);
		formVisualizar.setDisable(true);
		botaoVisualizar.setVisible(false);
		botaoVisualizar.setDisable(true);
	}
	
	private void disableFormAdicionar() {
		formAdicionar.setVisible(false);
		formAdicionar.setDisable(true);
		botaoAdicionar.setVisible(false);
		botaoAdicionar.setDisable(true);
	}
	
	private void enableVisualizar() {
		visualizar.setVisible(true);
		visualizar.setDisable(false);
	}
	
	private void preencheTabelaTurma(ObservableList<Turma> turmas) {
		tabela.getColumns().clear();
		TableColumn<Turma, String> colunaCodigo = new TableColumn<Turma, String>("Código");
		colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		
		TableColumn<Turma, String> colunaCurso = new TableColumn<Turma, String>("Curso");
		colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		
		TableColumn<Turma, String> colunaDiasDaSemana = new TableColumn<Turma, String>("Dias da Semana");
		colunaDiasDaSemana.setCellValueFactory(new PropertyValueFactory<>("diasDaSemana"));
		
		TableColumn<Turma, String> colunaHorario = new TableColumn<Turma, String>("Horário");
		colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
		
        tabela.getColumns().addAll(Arrays.asList(colunaCodigo, colunaCurso, colunaDiasDaSemana, colunaHorario));
		
		tabela.setItems(turmas);
	}
	
	private void preencheTabelaAlunos(ObservableList<Aluno> alunos) {
		tabela.getColumns().clear();
		TableColumn<Turma, String> colunaCpf = new TableColumn<Turma, String>("CPF");
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		
		TableColumn<Turma, String> colunaNumDeMatricula = new TableColumn<Turma, String>("Número de Matrícula");
		colunaNumDeMatricula.setCellValueFactory(new PropertyValueFactory<>("numeroDeMatricula"));
		
		TableColumn<Turma, String> colunaNome = new TableColumn<Turma, String>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
        tabela.getColumns().addAll(Arrays.asList(colunaNome, colunaNumDeMatricula, colunaCpf));
		
		tabela.setItems(alunos);
	}
	private void clickEvent() {
		tabela.setOnMouseClicked(new EventHandler<MouseEvent>() {			 
		    @Override
		    public void handle(MouseEvent click) {
		    	objetoSelecionado = tabela.getSelectionModel()
		                                                    .getSelectedItem();		        	  		        
		    }
		});
	}
	private void clean() {
		textField.setText("");
		textField1.setText("");
	}
	private boolean turmaJaTemProfessor(String codigo) {
		boolean temProf = false;
		ObservableList<Professor> lista = EscolaDeMusicaDB.getInstance().getProfessores();
		for(Professor prof: lista) {
			ObservableList<Turma> turmas= prof.getTurmas();
			for(Turma turma: turmas) {
				if(turma.getCodigo().equalsIgnoreCase(codigo)) {
					temProf = true;
					break;
				}
			}
		}
		return temProf;
	}
}
