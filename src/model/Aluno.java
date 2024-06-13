package model;

/**
 * Classe que representa um aluno na escola de música.
 */
public class Aluno {
    private String cpf; // CPF do aluno
    private String numeroDeMatricula; // Número de matrícula do aluno
    private String nome; // Nome do aluno

    /**
     * Obtém o CPF do aluno.
     * 
     * @return CPF do aluno
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do aluno.
     * 
     * @param cpf CPF do aluno
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém o número de matrícula do aluno.
     * 
     * @return Número de matrícula do aluno
     */
    public String getNumeroDeMatricula() {
        return numeroDeMatricula;
    }

    /**
     * Define o número de matrícula do aluno.
     * 
     * @param numeroDeMatricula Número de matrícula do aluno
     */
    public void setNumeroDeMatricula(String numeroDeMatricula) {
        this.numeroDeMatricula = numeroDeMatricula;
    }

    /**
     * Obtém o nome do aluno.
     * 
     * @return Nome do aluno
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do aluno.
     * 
     * @param nome Nome do aluno
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Cria um novo aluno e o adiciona ao banco de dados da escola de música.
     * 
     * @param nomeA Nome do aluno a ser criado
     * @param numeroDeMatriculaA Número de matrícula do aluno a ser criado
     * @param cpfA CPF do aluno a ser criado
     */
    public static void create(String nomeA, String numeroDeMatriculaA, String cpfA){
        Aluno aluno = new Aluno();
        aluno.setNome(nomeA);
        aluno.setNumeroDeMatricula(numeroDeMatriculaA);
        aluno.setCpf(cpfA);
        EscolaDeMusicaDB.getInstance().getAlunos().add(aluno);
    }
    
    /**
     * Remove um aluno do banco de dados da escola de música.
     * 
     * @param aluno Aluno a ser removido
     */
    public static void delete(Aluno aluno) {
        EscolaDeMusicaDB.getInstance().getAlunos().remove(aluno);
    }
    
    /**
     * Edita as informações de um aluno existente no banco de dados da escola de música.
     * 
     * @param aluno Aluno a ser editado
     * @param nomeA Novo nome do aluno
     * @param numeroDeMatriculaA Novo número de matrícula do aluno
     * @param cpfA Novo CPF do aluno
     */
    public static void edit(Aluno aluno, String nomeA, String numeroDeMatriculaA, String cpfA) {
        aluno.setNome(nomeA);
        aluno.setCpf(cpfA);
        aluno.setNumeroDeMatricula(numeroDeMatriculaA);
    }
}
