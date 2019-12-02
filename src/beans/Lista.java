package beans;

import java.util.Calendar;

public class Lista implements java.io.Serializable{
	
	private static final long serialVersionUID = 20110303013L;

	private int numero;
	private Nucleo nucleo;
	private int ano;
	private TipoEntrada entrada;
	private Calendar data;
	private long cpfOperador;
	private Socio socioDevedor;
	private String flSocio;
	private double valor;
	private double valorPago;
	private Calendar dataPagamento;
	private String reciboGerado; //numero/ano
	private String flContinua;
	private String flFechada;
	private int total;
	private int qtdePagamentos; 
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Nucleo getNucleo() {
		return nucleo;
	}
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public TipoEntrada getEntrada() {
		return entrada;
	}
	public void setEntrada(TipoEntrada entrada) {
		this.entrada = entrada;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public long getCpfOperador() {
		return cpfOperador;
	}
	public void setCpfOperador(long cpfOperador) {
		this.cpfOperador = cpfOperador;
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
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getValorPago() {
		return valorPago;
	}
	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
	public double getValorPendente() {
		return (this.valor - this.valorPago);
	}
	public Calendar getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getReciboGerado() {
		return reciboGerado;
	}
	public void setReciboGerado(String reciboGerado) {
		this.reciboGerado = reciboGerado;
	}
	public String getFlFechada() {
		return flFechada;
	}
	public void setFlFechada(String flFechada) {
		this.flFechada = flFechada;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getFlContinua() {
		return flContinua;
	}
	public void setFlContinua(String flContinua) {
		this.flContinua = flContinua;
	}
	public int getQtdePagamentos() {
		return qtdePagamentos;
	}
	public void setQtdePagamentos(int qtdePagamentos) {
		this.qtdePagamentos = qtdePagamentos;
	}
		
}
