package beans;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Saida implements java.io.Serializable {
	private static final long serialVersionUID = 100933001L;

	private Nucleo nucleo;
	private int numero;
	private TipoSaida saida;
	private int mes;
	private int ano;
	private Calendar data;
	private long cpfOperador;
	private TipoDocumento tipoDocumento;
	private String documento;
	private Date dataDocumento;
	private int fornecedor;
	private String flSocio;
	private double valor;
	private String flEstornada;
	private Socio ObjetoSocio;
	private NaoSocio ObjetoNaoSocio;
	private String flFechada;
	private String observacao;
	
	public Nucleo getNucleo() {
		return nucleo;
	}
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}
	public String getValorStr() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale ("pt", "BR"));   
		String s = nf.format(valor);
		
		return s;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public TipoSaida getSaida() {
		return saida;
	}
	public void setSaida(TipoSaida saida) {
		this.saida = saida;
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
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public int getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(int fornecedor) {
		this.fornecedor = fornecedor;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getFlSocio() {
		return flSocio;
	}
	public void setFlSocio(String flSocio) {
		this.flSocio = flSocio;
	}
	public String getFlEstornada() {
		return flEstornada;
	}
	public void setFlEstornada(String flEstornada) {
		this.flEstornada = flEstornada;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public NaoSocio getObjetoNaoSocio() {
		return ObjetoNaoSocio;
	}
	public void setObjetoNaoSocio(NaoSocio objetoNaoSocio) {
		ObjetoNaoSocio = objetoNaoSocio;
	}
	public Socio getObjetoSocio() {
		return ObjetoSocio;
	}
	public void setObjetoSocio(Socio objetoSocio) {
		ObjetoSocio = objetoSocio;
	}
	public String getFlFechada() {
		return flFechada;
	}
	public void setFlFechada(String flFechada) {
		this.flFechada = flFechada;
	}
	
}
