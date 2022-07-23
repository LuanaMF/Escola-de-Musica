package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Curso {
	
	private String nome;
	private String cargaHoraria;
	private ObservableList<Turma> turmas;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCargaHoraria() {
		return cargaHoraria;	
	}
	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	public ObservableList<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(ObservableList<Turma> turmas) {
		this.turmas = turmas;
	}
	public static void create(String nomeC, String cargaHorariaC){
		Curso curso = new Curso();
		curso.setNome(nomeC);
		curso.setCargaHoraria(cargaHorariaC);
		ObservableList<Turma> turmasC = FXCollections.observableArrayList();
		curso.setTurmas(turmasC);
		EscolaDeMusicaDB.getInstance().getCursos().add(curso);
	}
	
	public static void delete(Curso curso) {
		EscolaDeMusicaDB.getInstance().getCursos().remove(curso);
	}
	
	public static void edit(Curso curso, String nomeC, String cargaHorariaC) {
		curso.setNome(nomeC);
		curso.setCargaHoraria(cargaHorariaC);
	}
}
