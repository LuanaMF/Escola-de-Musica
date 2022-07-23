package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EscolaDeMusicaDB {
	
	private ObservableList<Aluno> alunos = FXCollections.observableArrayList();
	private ObservableList<Curso> cursos = FXCollections.observableArrayList();
	private ObservableList<Professor> professores = FXCollections.observableArrayList();
	private ObservableList<Turma> turmas = FXCollections.observableArrayList();
	private static EscolaDeMusicaDB instance;
	
	public static synchronized EscolaDeMusicaDB getInstance() {
		if(instance == null) {
			instance = new EscolaDeMusicaDB();
		}
		return instance;
	}
	
	public ObservableList<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(ObservableList<Aluno> alunos) {
		this.alunos = alunos;
	}
	public ObservableList<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(ObservableList<Curso> cursos) {
		this.cursos = cursos;
	}
	public ObservableList<Professor> getProfessores() {
		return professores;
	}
	public void setProfessores(ObservableList<Professor> professores) {
		this.professores = professores;
	}
	public ObservableList<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(ObservableList<Turma> turmas) {
		this.turmas = turmas;
	}
	
	
	
	
}
