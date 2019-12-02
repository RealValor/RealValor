package beans;

import java.io.Serializable;
import java.util.Date;

public class Notificacao implements Serializable{

	private static final long serialVersionUID = 1207711103L;

	private int nucleo;
	private Date datanotificacao;
	private String tipo;
	
	public int getNucleo() {
		return nucleo;
	}
	public void setNucleo(int nucleo) {
		this.nucleo = nucleo;
	}
	public Date getDatanotificacao() {
		return datanotificacao;
	}
	public void setDatanotificacao(Date datanotificacao) {
		this.datanotificacao = datanotificacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
}
