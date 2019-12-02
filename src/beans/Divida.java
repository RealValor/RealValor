package beans;

import java.util.Calendar;

public class Divida implements java.io.Serializable{
	
	private static final long serialVersionUID = 90105130L;

	private Saida saida;
	private Nucleo nucleo;
	private int ano;
	private int mes;
	private double valor;
	
	private int tipoSaida;
	private String numeroDocumento;

	private Calendar dataDocumento;
	private Calendar movimento;
	private Calendar vencimento;
	private Calendar pagamento;

	private String pago;
	
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
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Saida getSaida() {
		return saida;
	}
	public void setSaida(Saida saida) {
		this.saida = saida;
	}

	public int getTipoSaida() {
		return tipoSaida;
	}
	public void setTipoSaida(int tiposaida) {
		this.tipoSaida = tiposaida;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public String getPago() {
		return pago;
	}
	public void setPago(String pago) {
		this.pago = pago;
	}
	public Calendar getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Calendar dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public Calendar getMovimento() {
		return movimento;
	}
	public void setMovimento(Calendar movimento) {
		this.movimento = movimento;
	}
	public Calendar getVencimento() {
		return vencimento;
	}
	public void setVencimento(Calendar vencimento) {
		this.vencimento = vencimento;
	}
	public Calendar getPagamento() {
		return pagamento;
	}
	public void setPagamento(Calendar pagamento) {
		this.pagamento = pagamento;
	}
	
}
