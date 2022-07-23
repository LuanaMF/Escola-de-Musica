package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Turma {
	
	private String codigo;
	private Curso curso;
	private String diasDaSemana;
	private String horario;
	private ObservableList<Aluno> alunos;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCurso() {
		return curso.getNome();
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public String getDiasDaSemana() {
		return diasDaSemana;
	}
	public void setDiasDaSemana(String diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public ObservableList<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(ObservableList<Aluno> alunos) {
		this.alunos = alunos;
	}
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
	
	public static void delete(Turma turma) {
		EscolaDeMusicaDB.getInstance().getTurmas().remove(turma);
	}
	
	public static void edit(Turma turma, String codigoT, Curso cursoT, String diasDaSemanaT, String horarioT) {
		turma.setCodigo(codigoT);
		turma.setCurso(cursoT);
		turma.setDiasDaSemana(diasDaSemanaT);
		turma.setHorario(horarioT);
	}
}
