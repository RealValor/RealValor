package beans;

import logica.ConverteMes;

public class MovAtual implements java.io.Serializable{
	private static final long serialVersionUID = 30160401L;
	
	private int ano;
	private Nucleo nucleo;
	private int mes; 
	private String fechado;
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public Nucleo getNucleo() {
		return nucleo;
	}
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getFechado() {
		return fechado;
	}
	public void setFechado(String fechado) {
		this.fechado = fechado;
	}
	
	public String getMesExtenso() {
		return ConverteMes.numericoExtenso(this.mes);
	}
}
