package beans;

public class Cargo implements java.io.Serializable{

	private static final long serialVersionUID = 20191L;

	private int codigo;
	private String descricao;
	private String sigla;
	
	public Cargo( ){
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;	
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}	
}
