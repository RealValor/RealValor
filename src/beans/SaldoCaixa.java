package beans;

public class SaldoCaixa implements java.io.Serializable{
	
	private static final long serialVersionUID = 10104077L;
	
	private int ano;
	private int mes;
	private Nucleo nucleo;
	private double saldoAnteriorCaixa;
	private double saldoAnteriorBanco;
	private double saldoAnteriorDivida;
	private String fechado;
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public Nucleo getNucleo() {
		return nucleo;
	}
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}
	public double getSaldoAnteriorCaixa() {
		return saldoAnteriorCaixa;
	}
	public void setSaldoAnteriorCaixa(double saldoAnteriorCaixa) {
		this.saldoAnteriorCaixa = saldoAnteriorCaixa;
	}
	public double getSaldoAnteriorBanco() {
		return saldoAnteriorBanco;
	}
	public void setSaldoAnteriorBanco(double saldoAnteriorBanco) {
		this.saldoAnteriorBanco = saldoAnteriorBanco;
	}
	public double getSaldoAnteriorDivida() {
		return saldoAnteriorDivida;
	}
	public void setSaldoAnteriorDivida(double saldoAnteriorDivida) {
		this.saldoAnteriorDivida = saldoAnteriorDivida;
	}
	public String getFechado() {
		return fechado;
	}
	public void setFechado(String fechado) {
		this.fechado = fechado;
	}
}
