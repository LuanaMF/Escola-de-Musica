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

public class TelaCursosController {
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
	
	private Curso cursoSelecionado;
	
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
		if(cursoSelecionado != null) {
			formBotao.setText("Atualizar");
			atribuiValores();
			enableForm();
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione um curso para atualizá-lo");
		}
		
	}
	
	public void botaoExcluirAction() {
		mensagem.setText("");
		if(cursoSelecionado != null) {
			Curso.delete(cursoSelecionado);
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione um curso para exclui-lo");
		}
	}
	
	public void formBotaoAction() {
		mensagem.setText("");
		String texto = formBotao.getText();
		
		boolean tudoCerto = verificaInformacoes();
		
		if(tudoCerto) {
			if(texto.equals("Adicionar")){
				createCurso();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Curso adicionado com sucesso!");
				disableForm();
			}
			else if(texto.equals("Atualizar")) {
				editCurso();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Curso atualizado com sucesso!");
				disableForm();
			}
		}
	}
	
	private void atribuiValores(){
		campoNome.setText(cursoSelecionado.getNome());
		campoCargaHoraria.setText(cursoSelecionado.getCargaHoraria());
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
		colunaCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
		tabelaCursos.setItems(EscolaDeMusicaDB.getInstance().getCursos());
	}
	
	private void createCurso(){
		Curso.create(campoNome.getText(), campoCargaHoraria.getText());
	}
	
	private void editCurso() {
		Curso.edit(cursoSelecionado, campoNome.getText(), campoCargaHoraria.getText());
		int index = tabelaCursos.getItems().indexOf(cursoSelecionado);
		tabelaCursos.getItems().set(index, cursoSelecionado);
	}
	
	private boolean verificaInformacoes() {
		mensagem.setText("");
		boolean nome = !campoNome.getText().isEmpty() && !campoNome.getText().isBlank();
		boolean cargaHoraria = !campoCargaHoraria.getText().isEmpty() && !campoCargaHoraria.getText().isBlank();
		boolean tudoCerto = nome && cargaHoraria;
		
		if(!tudoCerto) {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Preencha todos os campos para prosseguir");
		}
		
		return tudoCerto;
	}
	private void clean() {
		campoNome.setText("");
		campoCargaHoraria.setText("");
	}
	private void clickEvent() {
		tabelaCursos.setOnMouseClicked(new EventHandler<MouseEvent>() {			 
		    @Override
		    public void handle(MouseEvent click) {
		    	cursoSelecionado = tabelaCursos.getSelectionModel()
		                                                    .getSelectedItem();		        	  		        
		    }
		});
	}
}
