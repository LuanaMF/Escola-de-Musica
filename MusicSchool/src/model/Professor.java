package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Professor {
	private String cpf;
	private String nome;
	private ObservableList<Turma> turmas;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ObservableList<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(ObservableList<Turma> turmas) {
		this.turmas = turmas;
	}
	public static void create(String nomeP, String cpf){
		Professor professor = new Professor();
		professor.setNome(nomeP);
		professor.setCpf(cpf);
		ObservableList<Turma> turmasP = FXCollections.observableArrayList();
		professor.setTurmas(turmasP);
		EscolaDeMusicaDB.getInstance().getProfessores().add(professor);
	}
	
	public static void delete(Professor professor) {
		EscolaDeMusicaDB.getInstance().getProfessores().remove(professor);
	}
	
	public static void edit(Professor professor, String nomeP, String cpf) {
		professor.setNome(nomeP);
		professor.setCpf(cpf);
	}
}
