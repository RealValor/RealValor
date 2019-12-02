package beans;

import java.util.Calendar;

import DAO.LoginDAO;
import DAO.StuDAOException;

public class Recibo implements java.io.Serializable{

	private static final long serialVersionUID = 2010701L;
	
	public int numero;
	public int nucleo;
	private TipoEntrada entrada;
	private int mes;
	private int ano;
	private Calendar data;
	private long cpfOperador;
	private Socio socioDevedor;
	private Socio socioPagador;
	private String flSocio;
	private NaoSocio naoSocioPagador;
	private int meses[] = new int[ 12 ];
	private double valor;
	private double valorMensalidade;
	private String observacao;
	private String fechado;
	private int numeroLista;
	private int parcelasRestantes;
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getNucleo() {
		return nucleo;
	}
	public void setNucleo(int nucleo) {
		this.nucleo = nucleo;
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
	public Socio getSocioDevedor() {
		return socioDevedor;
	}
	public void setSocioDevedor(Socio socioDevedor) {
		this.socioDevedor = socioDevedor;
	}
	public Socio getSocioPagador() {
		return socioPagador;
	}
	public void setSocioPagador(Socio socioPagador) {
		this.socioPagador = socioPagador;
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
	public void setMeses(int meses[]) {
		this.meses = meses;
	}
	public int[] getMeses() {
		return meses;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public double getValorMensalidade() {
		return valorMensalidade;
	}
	public void setValorMensalidade(double valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}
	public String getFechado() {
		return fechado;
	}
	public void setFechado(String fechado) {
		this.fechado = fechado;
	}
	public NaoSocio getNaoSocioPagador() {
		return naoSocioPagador;
	}
	public void setNaoSocioPagador(NaoSocio naoSocioPagador) {
		this.naoSocioPagador = naoSocioPagador;
	}
	public int getNumeroLista() {
		return numeroLista;
	}
	public void setNumeroLista(int numeroLista) {
		this.numeroLista = numeroLista;
	}
	
	public Login getOperador() throws StuDAOException{
		LoginDAO objOperadorDAO = new LoginDAO();
		Login objOperador = new Login();
		Nucleo objNucleo = new Nucleo();
		objNucleo.setCodigo(this.nucleo);
		
		objOperador.setCpf(this.cpfOperador);
		objOperador.setNucleo(objNucleo);
		objOperador = objOperadorDAO.consultarUsuNome(objOperador);
		
		return objOperador;
	}
	
	public int getParcelasRestantes() {
		return parcelasRestantes;
	}
	
	public void setParcelasRestantes(int parcelasRestantes) {
		this.parcelasRestantes = parcelasRestantes;
	}

}
