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
	
	private Turma turmaSelecionado;
	
	@FXML
	public void initialize() {
		
		preencheTabela();
		disableForm();
		clickEvent();
	}
	
	public void botaoAdicionarAction() {
		
		clean();
		formBotao.setText("Adicionar");
		enableForm();
	}
	
	public void botaoEditarAction() {
		mensagem.setText("");
		if(turmaSelecionado != null) {
			formBotao.setText("Atualizar");
			atribuiValores();
			enableForm();
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione uma turma para atualizá-la");
		}
		
	}
	
	public void botaoExcluirAction() {
		mensagem.setText("");
		disableForm();
		if(turmaSelecionado != null) {
			Turma.delete(turmaSelecionado);
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione uma turma para exclui-la");
		}
	}
	
	public void formBotaoAction() {
		mensagem.setText("");
		String texto = formBotao.getText();
		
		boolean tudoCerto = verificaInformacoes();
		int index = findCurso();
		if(index != -1) {
			Curso curso = EscolaDeMusicaDB.getInstance().getCursos().get(index);
			if(tudoCerto) {
				if(texto.equals("Adicionar")){
					createTurma(curso);
					mensagem.setTextFill(Color.GREEN);
					mensagem.setText("Turma adicionada com sucesso!");
					disableForm();
				}
				else if(texto.equals("Atualizar")) {
					editTurma(curso);
					mensagem.setTextFill(Color.GREEN);
					mensagem.setText("Turma atualizada com sucesso!");
					disableForm();
				}
			}
		}
		else {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Curso não encontrado! Verifique o nome e tente novamente");
		}
		
	}
	
	private void clean() {
		campoCodigo.setText("");
		campoCurso.setText("");
		campoHorario.setText("");
		campoDiasDaSemana.setText("");
	}
	private void atribuiValores(){
		campoCodigo.setText(turmaSelecionado.getCodigo());
		campoCurso.setText(turmaSelecionado.getCurso());
		campoHorario.setText(turmaSelecionado.getHorario());
		campoDiasDaSemana.setText(turmaSelecionado.getDiasDaSemana());
	}
	
	private void disableForm() {		
		form.setVisible(false);
		form.setDisable(true);
		formBotao.setVisible(false);
		formBotao.setDisable(true);
	}
	
	private void enableForm() {
		form.setVisible(true);
		form.setDisable(false);
		formBotao.setVisible(true);
		formBotao.setDisable(false);
	}
	
	private void preencheTabela(){		
		colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
		colunaDiasDaSemana.setCellValueFactory(new PropertyValueFactory<>("diasDaSemana"));
		tabelaTurmas.setItems(EscolaDeMusicaDB.getInstance().getTurmas());
	}
	
	private void createTurma(Curso curso){
		Turma.create(campoCodigo.getText(), curso, campoDiasDaSemana.getText(), campoHorario.getText());
	}
	
	private void editTurma(Curso curso) {
		Turma.edit(turmaSelecionado, campoCodigo.getText(), curso, campoDiasDaSemana.getText(), campoHorario.getText());
		int index = tabelaTurmas.getItems().indexOf(turmaSelecionado);
		tabelaTurmas.getItems().set(index, turmaSelecionado);
	}
	
	private int findCurso() {
		int index = -1;
		ObservableList<Curso> lista = EscolaDeMusicaDB.getInstance().getCursos();
		String nome = campoCurso.getText();
		for(Curso curso: lista){
			if(curso.getNome().equalsIgnoreCase(nome)) {
				index = lista.indexOf(curso);
			}
		}
		return index;
	}
	private boolean verificaInformacoes() {
		mensagem.setText("");
		boolean codigo = !campoCodigo.getText().isEmpty() && !campoCodigo.getText().isBlank();
		boolean horario = !campoHorario.getText().isEmpty() && !campoHorario.getText().isBlank();
		boolean curso = !campoCurso.getText().isEmpty() && !campoCurso.getText().isBlank();
		boolean duasDaSemana = !campoDiasDaSemana.getText().isEmpty() && !campoDiasDaSemana.getText().isBlank();
		boolean tudoCerto = codigo && horario && curso && duasDaSemana;
		
		if(!tudoCerto) {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Preencha todos os campos para prosseguir");
		}
		
		return tudoCerto;
	}
	private void clickEvent() {
		tabelaTurmas.setOnMouseClicked(new EventHandler<MouseEvent>() {			 
		    @Override
		    public void handle(MouseEvent click) {
		    	turmaSelecionado = tabelaTurmas.getSelectionModel()
		                                                    .getSelectedItem();		        	  		        
		    }
		});
	}
}
