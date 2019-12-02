package beans;

import java.util.List;

public class Regiao implements java.io.Serializable {
	private static final long serialVersionUID = 10185109031L;
	
	private int codigo;
	private String descricao;
	private String complemento;
	private List<Nucleo> listanucleo; 
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int regiao) {
		this.codigo = regiao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public List<Nucleo> getListanucleo() {
		return listanucleo;
	}
	public void setListanucleo(List<Nucleo> listanucleo) {
		this.listanucleo = listanucleo;
	}

	
}
