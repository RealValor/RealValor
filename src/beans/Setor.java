package beans;

public class Setor implements java.io.Serializable{

	private static final long serialVersionUID = 200032L;

	private int codigo;
	private String descricao;
	//private int tipo;
	
	public Setor( ){
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
	/*
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	*/
}
