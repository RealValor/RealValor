package beans;

import java.text.NumberFormat;
import java.util.Locale;

public class TipoEntrada implements java.io.Serializable{
	
	private static final long serialVersionUID = 200100302L;

	private int codigo;
	private String descricao;
	private double valor;
	private String mensal;
	private String ativo;
	private Nucleo nucleo;
	
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

	public double getValor() {
		return valor;		
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getValorStr() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale ("pt", "BR"));   
		String s = nf.format(valor);
		
		return s;
	}
	public String getMensal() {
		return mensal;
	}
	public void setMensal(String mensal) {
		this.mensal = mensal;
	}
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	public Nucleo getNucleo() {
		return nucleo;
	}
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}
	
}
