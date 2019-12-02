package beans;

import java.util.Date;

public class HistoricoDevedor implements java.io.Serializable{

	private static final long serialVersionUID = 2019999803L;

	private int nucleo;
	private int recibo;
	private int mes;	
	private int ano;	
	private Date data;
	private long cpfOperador;
	private int socioPagador;
	private Socio socioDevedor;
	private String flSocio;
	private TipoEntrada entrada;
	private double valor;		
	private String excluido;
	private int numeroLista;
	
	public int getNucleo() {
		return nucleo;
	}
	public void setNucleo(int nucleo) {
		this.nucleo = nucleo;
	}
	public int getRecibo() {
		return recibo;
	}
	public void setRecibo(int recibo) {
		this.recibo = recibo;
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public long getCpfOperador() {
		return cpfOperador;
	}
	public void setCpfOperador(long cpfOperador) {
		this.cpfOperador = cpfOperador;
	}
	public int getSocioPagador() {
		return socioPagador;
	}
	public void setSocioPagador(int socioPagador) {
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
	public TipoEntrada getEntrada() {
		return entrada;
	}
	public void setEntrada(TipoEntrada entrada) {
		this.entrada = entrada;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getExcluido() {
		return excluido;
	}
	public void setExcluido(String excluido) {
		this.excluido = excluido;
	}
	public int getNumeroLista() {
		return numeroLista;
	}
	public void setNumeroLista(int numeroLista) {
		this.numeroLista = numeroLista;
	}
	
}

