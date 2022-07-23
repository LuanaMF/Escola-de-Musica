package model;

public class Aluno {
	private String cpf;
	private String numeroDeMatricula;
	private String nome;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNumeroDeMatricula() {
		return numeroDeMatricula;
	}

	public void setNumeroDeMatricula(String numeroDeMatricula) {
		this.numeroDeMatricula = numeroDeMatricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public static void create(String nomeA, String numeroDeMatriculaA, String cpfA){
		Aluno aluno = new Aluno();
		aluno.setNome(nomeA);
		aluno.setNumeroDeMatricula(numeroDeMatriculaA);
		aluno.setCpf(cpfA);
		EscolaDeMusicaDB.getInstance().getAlunos().add(aluno);
	}
	
	public static void delete(Aluno aluno) {
		EscolaDeMusicaDB.getInstance().getAlunos().remove(aluno);
	}
	
	public static void edit(Aluno aluno, String nomeA, String numeroDeMatriculaA, String cpfA) {
		aluno.setNome(nomeA);
		aluno.setCpf(cpfA);
		aluno.setNumeroDeMatricula(numeroDeMatriculaA);
	}
}
