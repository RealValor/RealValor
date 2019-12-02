package beans;

import java.util.Calendar;

import logica.ConverteMes;

public class HistoricoRecebimento implements java.io.Serializable{

	private static final long serialVersionUID = 1009213L;
	
	private int recibo;
	private Nucleo nucleo;
	private int mes;	
	private int ano;	
	private Calendar data;
	private long cpfOperador;
	private Socio socioPagador;
	private Socio socioDevedor;
	private String flSocio;
	private TipoEntrada entrada;
	private double valor;		
	private String excluido;
	private int quantidade;	
	private String observacao;
	
	public Nucleo getNucleo() {
		return nucleo;
	}
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}
	public long getCpfOperador() {
		return cpfOperador;
	}
	public void setCpfOperador(long cpfOperador) {
		this.cpfOperador = cpfOperador;
	}
	public Socio getSocioPagador() {
		return socioPagador;
	}
	public void setSocioPagador(Socio socioPagador) {
		this.socioPagador = socioPagador;
	}
	public Socio getSocioDevedor() {
		return socioDevedor;
	}
	public void setSocioDevedor(Socio socioDevedor) {
		this.socioDevedor = socioDevedor;
	}
	public String getFlSocio() {
		return flSocio;
	}
	public void setFlSocio(String flSocio) {
		this.flSocio = flSocio;
	}
	public String getExcluido() {
		return excluido;
	}
	public void setExcluido(String excluido) {
		this.excluido = excluido;
	}
	public TipoEntrada getEntrada() {
		return entrada;
	}
	public void setEntrada(TipoEntrada entrada) {
		this.entrada = entrada;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getRecibo() {
		return recibo;
	}
	public void setRecibo(int recibo) {
		this.recibo = recibo;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getMesExtenso() {
		return ConverteMes.numericoExtenso(this.mes);
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
