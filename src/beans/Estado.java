package beans;

public class Estado implements java.io.Serializable{
	
	private static final long serialVersionUID = 31450903L;

	private int codigo;
	private String nome;
	private String uf;
	private String regiaoUF;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getRegiaoUF() {
		return regiaoUF;
	}
	public void setRegiaoUF(String regiaoUF) {
		this.regiaoUF = regiaoUF;
	}

	
}
