package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe que representa o banco de dados da escola de música.
 * Armazena listas de alunos, cursos, professores e turmas.
 */
public class EscolaDeMusicaDB {
	
	private ObservableList<Aluno> alunos = FXCollections.observableArrayList(); // Lista de alunos cadastrados na escola
	private ObservableList<Curso> cursos = FXCollections.observableArrayList(); // Lista de cursos oferecidos pela escola
	private ObservableList<Professor> professores = FXCollections.observableArrayList(); // Lista de professores da escola
	private ObservableList<Turma> turmas = FXCollections.observableArrayList(); // Lista de turmas disponíveis na escola
	private static EscolaDeMusicaDB instance; // Instância única do banco de dados da escola
	
	/**
	 * Método estático para obter a instância única do banco de dados.
	 * Utiliza o padrão Singleton para garantir uma única instância.
	 * 
	 * @return Instância única do banco de dados da escola
	 */
	public static synchronized EscolaDeMusicaDB getInstance() {
		if(instance == null) {
			instance = new EscolaDeMusicaDB();
		}
		return instance;
	}
	
	/**
	 * Obtém a lista de alunos cadastrados na escola.
	 * 
	 * @return Lista de alunos
	 */
	public ObservableList<Aluno> getAlunos() {
		return alunos;
	}
	
	/**
	 * Define a lista de alunos cadastrados na escola.
	 * 
	 * @param alunos Lista de alunos a ser definida
	 */
	public void setAlunos(ObservableList<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	/**
	 * Obtém a lista de cursos oferecidos pela escola.
	 * 
	 * @return Lista de cursos
	 */
	public ObservableList<Curso> getCursos() {
		return cursos;
	}
	
	/**
	 * Define a lista de cursos oferecidos pela escola.
	 * 
	 * @param cursos Lista de cursos a ser definida
	 */
	public void setCursos(ObservableList<Curso> cursos) {
		this.cursos = cursos;
	}
	
	/**
	 * Obtém a lista de professores da escola.
	 * 
	 * @return Lista de professores
	 */
	public ObservableList<Professor> getProfessores() {
		return professores;
	}
	
	/**
	 * Define a lista de professores da escola.
	 * 
	 * @param professores Lista de professores a ser definida
	 */
	public void setProfessores(ObservableList<Professor> professores) {
		this.professores = professores;
	}
	
	/**
	 * Obtém a lista de turmas disponíveis na escola.
	 * 
	 * @return Lista de turmas
	 */
	public ObservableList<Turma> getTurmas() {
		return turmas;
	}
	
	/**
	 * Define a lista de turmas disponíveis na escola.
	 * 
	 * @param turmas Lista de turmas a ser definida
	 */
	public void setTurmas(ObservableList<Turma> turmas) {
		this.turmas = turmas;
	}
}
