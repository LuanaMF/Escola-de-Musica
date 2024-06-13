package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe que representa um professor da escola de música.
 * Armazena informações como CPF, nome e as turmas associadas ao professor.
 */
public class Professor {
	
	private String cpf; // CPF do professor
	private String nome; // Nome do professor
	private ObservableList<Turma> turmas; // Lista de turmas ministradas pelo professor
	
	/**
	 * Obtém o CPF do professor.
	 * 
	 * @return CPF do professor
	 */
	public String getCpf() {
		return cpf;
	}
	
	/**
	 * Define o CPF do professor.
	 * 
	 * @param cpf CPF a ser definido
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	/**
	 * Obtém o nome do professor.
	 * 
	 * @return Nome do professor
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Define o nome do professor.
	 * 
	 * @param nome Nome a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Obtém a lista de turmas ministradas pelo professor.
	 * 
	 * @return Lista de turmas do professor
	 */
	public ObservableList<Turma> getTurmas() {
		return turmas;
	}
	
	/**
	 * Define a lista de turmas ministradas pelo professor.
	 * 
	 * @param turmas Lista de turmas a ser definida
	 */
	public void setTurmas(ObservableList<Turma> turmas) {
		this.turmas = turmas;
	}
	
	/**
	 * Método estático para criar um novo professor.
	 * 
	 * @param nomeP Nome do professor a ser criado
	 * @param cpf CPF do professor a ser criado
	 */
	public static void create(String nomeP, String cpf){
		Professor professor = new Professor();
		professor.setNome(nomeP);
		professor.setCpf(cpf);
		ObservableList<Turma> turmasP = FXCollections.observableArrayList();
		professor.setTurmas(turmasP);
		EscolaDeMusicaDB.getInstance().getProfessores().add(professor);
	}
	
	/**
	 * Método estático para excluir um professor.
	 * 
	 * @param professor Professor a ser excluído
	 */
	public static void delete(Professor professor) {
		EscolaDeMusicaDB.getInstance().getProfessores().remove(professor);
	}
	
	/**
	 * Método estático para editar informações de um professor.
	 * 
	 * @param professor Professor a ser editado
	 * @param nomeP Novo nome do professor
	 * @param cpf Novo CPF do professor
	 */
	public static void edit(Professor professor, String nomeP, String cpf) {
		professor.setNome(nomeP);
		professor.setCpf(cpf);
	}
}
