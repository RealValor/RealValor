package beans;


public class Nucleo implements java.io.Serializable{
	
	private static final long serialVersionUID = 21011033001L;

	private int codigo;
	private Regiao regiao;
	private String nome;
	private String cnpj;
	private String logradouro;
	private Bairro bairro;
	private String cep;
	private Cidade cidade;
	private Estado estado;
	private Banco banco_recebimento; 
	private String email;
	private int totalsocios;
	private String observacao;
	private String offline;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
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
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getLogradouro() {
		return logradouro;
	}	
	public Bairro getBairro() {
		return bairro;
	}
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Banco getBanco_recebimento() {
		return banco_recebimento;
	}
	public void setBanco_recebimento(Banco banco_recebimento) {
		this.banco_recebimento = banco_recebimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public int getTotalsocios() {
		return totalsocios;
	}
	public void setTotalsocios(int totalsocios) {
		this.totalsocios = totalsocios;
	}
	public String getOffline() {
		return offline;
	}
	public void setOffline(String offline) {
		this.offline = offline;
	}
	
}
