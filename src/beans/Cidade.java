package beans;

public class Cidade implements java.io.Serializable{

	private static final long serialVersionUID = 201001091L;

	private int codigo;
	private String nome;
	private String UF;
	private int codigoIBGE;
	
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

	public String getUF() {
		return UF;
	}

	public void setUF(String uF) {
		UF = uF;
	}
	public int getCodigoIBGE() {
		return codigoIBGE;
	}
	public void setCodigoIBGE(int codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	
	
}
