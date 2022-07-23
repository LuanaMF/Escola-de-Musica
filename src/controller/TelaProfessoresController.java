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

public class TelaProfessoresController {

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
	
	private Professor professorSelecionado;
	
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
		if(professorSelecionado != null) {
			formBotao.setText("Atualizar");
			atribuiValores();
			enableForm();
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione um(a) professor(a) para atualizá-lo(a)");
		}
		
	}
	
	public void botaoExcluirAction() {
		mensagem.setText("");
		if(professorSelecionado != null) {
			Professor.delete(professorSelecionado);
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione um(a) aluno(a) para exclui-lo(a)");
		}
	}
	
	public void formBotaoAction() {
		mensagem.setText("");
		String texto = formBotao.getText();
		
		boolean tudoCerto = verificaInformacoes();
		
		if(tudoCerto) {
			if(texto.equals("Adicionar")){
				createProfessor();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Professor(a) adicionado(a) com sucesso!");
				disableForm();
			}
			else if(texto.equals("Atualizar")) {
				editProfessor();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Professor(a) atualizado(a) com sucesso!");
				disableForm();
			}
		}
	}
	
	private void atribuiValores(){
		campoNome.setText(professorSelecionado.getNome());
		campoCpf.setText(professorSelecionado.getCpf());
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
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tabelaProfessores.setItems(EscolaDeMusicaDB.getInstance().getProfessores());
	}
	
	private void createProfessor(){
		Professor.create(campoNome.getText(), campoCpf.getText());
	}
	
	private void editProfessor() {
		Professor.edit(professorSelecionado, campoNome.getText(), campoCpf.getText());
		int index = tabelaProfessores.getItems().indexOf(professorSelecionado);
		tabelaProfessores.getItems().set(index, professorSelecionado);
	}
	
	private boolean verificaInformacoes() {
		mensagem.setText("");
		boolean cpf = !campoCpf.getText().isEmpty() && !campoCpf.getText().isBlank();
		boolean nome = !campoNome.getText().isEmpty() && !campoNome.getText().isBlank();
		boolean tudoCerto = cpf && nome;
		
		if(!tudoCerto) {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Preencha todos os campos para prosseguir");
		}
		
		return tudoCerto;
	}
	private void clean() {
		campoNome.setText("");
		campoCpf.setText("");
	}
	private void clickEvent() {
		tabelaProfessores.setOnMouseClicked(new EventHandler<MouseEvent>() {			 
		    @Override
		    public void handle(MouseEvent click) {
		    	professorSelecionado = tabelaProfessores.getSelectionModel()
		                                                    .getSelectedItem();		        	  		        
		    }
		});
	}
}
