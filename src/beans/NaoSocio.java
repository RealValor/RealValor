package beans;



public class NaoSocio implements java.io.Serializable{

	private static final long serialVersionUID = 4090110L;
	
	private int codigo;
	private Regiao regiao;
	private String nome;
	private String telefone;
	private String cpfCnpj;
	private String email;
	
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/*
	public String getCpfStr() throws StuDAOException {
		return CPFString.converteCPF(this.cpfCnpj); 
		//posteriormente alterar a classe CPFString para suportar CNPJ
	}
	*/
}
