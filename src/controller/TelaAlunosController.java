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

public class TelaAlunosController {
	
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
	
	private Aluno alunoSelecionado;
	
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
		if(alunoSelecionado != null) {
			formBotao.setText("Atualizar");
			atribuiValores();
			enableForm();
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione um aluno para atualizá-lo");
		}
		
	}
	
	public void botaoExcluirAction() {
		mensagem.setText("");
		if(alunoSelecionado != null) {
			Aluno.delete(alunoSelecionado);
		}
		else{
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Selecione um aluno para exclui-lo");
		}
	}
	
	public void formBotaoAction() {
		mensagem.setText("");
		String texto = formBotao.getText();
		
		boolean tudoCerto = verificaInformacoes();
		
		if(tudoCerto) {
			if(texto.equals("Adicionar")){
				createAluno();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Aluno adicionado com sucesso!");
				disableForm();
			}
			else if(texto.equals("Atualizar")) {
				editAluno();
				mensagem.setTextFill(Color.GREEN);
				mensagem.setText("Aluno atualizado com sucesso!");
				disableForm();
			}
		}
	}
	
	private void atribuiValores(){
		campoNome.setText(alunoSelecionado.getNome());
		campoCpf.setText(alunoSelecionado.getCpf());
		campoNumMatricula.setText(alunoSelecionado.getNumeroDeMatricula());
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
		colunaNumMatricula.setCellValueFactory(new PropertyValueFactory<>("numeroDeMatricula"));
		tabelaAlunos.setItems(EscolaDeMusicaDB.getInstance().getAlunos());
	}
	
	private void createAluno(){
		Aluno.create(campoNome.getText(), campoNumMatricula.getText(), campoCpf.getText());
	}
	
	private void editAluno() {
		Aluno.edit(alunoSelecionado, campoNome.getText(), campoNumMatricula.getText(), campoCpf.getText());
		int index = tabelaAlunos.getItems().indexOf(alunoSelecionado);
		tabelaAlunos.getItems().set(index, alunoSelecionado);
	}
	
	private boolean verificaInformacoes() {
		mensagem.setText("");
		boolean cpf = !campoCpf.getText().isEmpty() && !campoCpf.getText().isBlank();
		boolean nome = !campoNome.getText().isEmpty() && !campoNome.getText().isBlank();
		boolean numMatricula = !campoNumMatricula.getText().isEmpty() && !campoNumMatricula.getText().isBlank();
		boolean tudoCerto = cpf && nome && numMatricula;
		
		if(!tudoCerto) {
			mensagem.setTextFill(Color.RED);
			mensagem.setText("Preencha todos os campos para prosseguir");
		}
		
		return tudoCerto;
	}
	private void clean() {
		campoCpf.setText("");
		campoNome.setText("");
		campoNumMatricula.setText("");
	}
	private void clickEvent() {
		tabelaAlunos.setOnMouseClicked(new EventHandler<MouseEvent>() {			 
		    @Override
		    public void handle(MouseEvent click) {
		    	alunoSelecionado = tabelaAlunos.getSelectionModel()
		                                                    .getSelectedItem();		        	  		        
		    }
		});
	}
}
