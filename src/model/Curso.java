package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe que representa um curso na escola de música.
 */
public class Curso {
	
	private String nome; // Nome do curso
	private String cargaHoraria; // Carga horária do curso
	private ObservableList<Turma> turmas; // Lista de turmas associadas ao curso
	
	/**
	 * Obtém o nome do curso.
	 * 
	 * @return Nome do curso
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Define o nome do curso.
	 * 
	 * @param nome Nome do curso
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Obtém a carga horária do curso.
	 * 
	 * @return Carga horária do curso
	 */
	public String getCargaHoraria() {
		return cargaHoraria;	
	}
	
	/**
	 * Define a carga horária do curso.
	 * 
	 * @param cargaHoraria Carga horária do curso
	 */
	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	/**
	 * Obtém a lista de turmas associadas ao curso.
	 * 
	 * @return Lista de turmas associadas ao curso
	 */
	public ObservableList<Turma> getTurmas() {
		return turmas;
	}
	
	/**
	 * Define a lista de turmas associadas ao curso.
	 * 
	 * @param turmas Lista de turmas associadas ao curso
	 */
	public void setTurmas(ObservableList<Turma> turmas) {
		this.turmas = turmas;
	}
	
	/**
	 * Cria um novo curso e o adiciona ao banco de dados da escola de música.
	 * 
	 * @param nomeC Nome do curso a ser criado
	 * @param cargaHorariaC Carga horária do curso a ser criado
	 */
	public static void create(String nomeC, String cargaHorariaC){
		Curso curso = new Curso();
		curso.setNome(nomeC);
		curso.setCargaHoraria(cargaHorariaC);
		ObservableList<Turma> turmasC = FXCollections.observableArrayList();
		curso.setTurmas(turmasC);
		EscolaDeMusicaDB.getInstance().getCursos().add(curso);
	}
	
	/**
	 * Remove um curso do banco de dados da escola de música.
	 * 
	 * @param curso Curso a ser removido
	 */
	public static void delete(Curso curso) {
		EscolaDeMusicaDB.getInstance().getCursos().remove(curso);
	}
	
	/**
	 * Edita as informações de um curso existente no banco de dados da escola de música.
	 * 
	 * @param curso Curso a ser editado
	 * @param nomeC Novo nome do curso
	 * @param cargaHorariaC Nova carga horária do curso
	 */
	public static void edit(Curso curso, String nomeC, String cargaHorariaC) {
		curso.setNome(nomeC);
		curso.setCargaHoraria(cargaHorariaC);
	}
}
