package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe que representa uma turma da escola de música.
 * Armazena informações como código, curso associado, dias da semana e horário das aulas,
 * além da lista de alunos matriculados na turma.
 */
public class Turma {
	
	private String codigo; // Código da turma
	private Curso curso; // Curso associado à turma
	private String diasDaSemana; // Dias da semana em que a turma ocorre
	private String horario; // Horário das aulas da turma
	private ObservableList<Aluno> alunos; // Lista de alunos matriculados na turma
	
	/**
	 * Obtém o código da turma.
	 * 
	 * @return Código da turma
	 */
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * Define o código da turma.
	 * 
	 * @param codigo Código da turma a ser definido
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Obtém o nome do curso associado à turma.
	 * 
	 * @return Nome do curso associado à turma
	 */
	public String getCurso() {
		return curso.getNome();
	}
	
	/**
	 * Define o curso associado à turma.
	 * 
	 * @param curso Curso a ser associado à turma
	 */
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	/**
	 * Obtém os dias da semana em que a turma ocorre.
	 * 
	 * @return Dias da semana da turma
	 */
	public String getDiasDaSemana() {
		return diasDaSemana;
	}
	
	/**
	 * Define os dias da semana em que a turma ocorre.
	 * 
	 * @param diasDaSemana Dias da semana da turma a serem definidos
	 */
	public void setDiasDaSemana(String diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}
	
	/**
	 * Obtém o horário das aulas da turma.
	 * 
	 * @return Horário das aulas da turma
	 */
	public String getHorario() {
		return horario;
	}
	
	/**
	 * Define o horário das aulas da turma.
	 * 
	 * @param horario Horário das aulas da turma a ser definido
	 */
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	/**
	 * Obtém a lista de alunos matriculados na turma.
	 * 
	 * @return Lista de alunos matriculados na turma
	 */
	public ObservableList<Aluno> getAlunos() {
		return alunos;
	}
	
	/**
	 * Define a lista de alunos matriculados na turma.
	 * 
	 * @param alunos Lista de alunos a ser definida na turma
	 */
	public void setAlunos(ObservableList<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	/**
	 * Método estático para criar uma nova turma.
	 * 
	 * @param codigoT Código da turma a ser criada
	 * @param cursoT Curso associado à turma
	 * @param diasDaSemanaT Dias da semana da turma
	 * @param horarioT Horário das aulas da turma
	 */
	public static void create(String codigoT, Curso cursoT, String diasDaSemanaT, String horarioT){
		Turma turma = new Turma();
		turma.setCodigo(codigoT);
		turma.setCurso(cursoT);
		turma.setDiasDaSemana(diasDaSemanaT);
		turma.setHorario(horarioT);
		ObservableList<Aluno> alunosT = FXCollections.observableArrayList();
		turma.setAlunos(alunosT);
		EscolaDeMusicaDB.getInstance().getTurmas().add(turma);
	}
	
	/**
	 * Método estático para excluir uma turma.
	 * 
	 * @param turma Turma a ser excluída
	 */
	public static void delete(Turma turma) {
		EscolaDeMusicaDB.getInstance().getTurmas().remove(turma);
	}
	
	/**
	 * Método estático para editar informações de uma turma.
	 * 
	 * @param turma Turma a ser editada
	 * @param codigoT Novo código da turma
	 * @param cursoT Novo curso associado à turma
	 * @param diasDaSemanaT Novos dias da semana da turma
	 * @param horarioT Novo horário das aulas da turma
	 */
	public static void edit(Turma turma, String codigoT, Curso cursoT, String diasDaSemanaT, String horarioT) {
		turma.setCodigo(codigoT);
		turma.setCurso(cursoT);
		turma.setDiasDaSemana(diasDaSemanaT);
		turma.setHorario(horarioT);
	}
}
