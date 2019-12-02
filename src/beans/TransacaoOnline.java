package beans;

import java.util.Calendar;

import DAO.LoginDAO;
import DAO.StuDAOException;

public class TransacaoOnline implements java.io.Serializable{

	private static final long serialVersionUID = 201071L;
	
	public int reciboTemp;
	private int anoData;
	public int recibo;
	private String transacao;
	private int status;
	private Calendar data;
	private long cpfOperador;
	private Socio socioPagador;

	public int getReciboTemp() {
		return reciboTemp;
	}


	public void setReciboTemp(int reciboTemp) {
		this.reciboTemp = reciboTemp;
	}


	public int getAnoData() {
		return anoData;
	}


	public void setAnoData(int anoData) {
		this.anoData = anoData;
	}


	public int getRecibo() {
		return recibo;
	}


	public void setRecibo(int recibo) {
		this.recibo = recibo;
	}


	public String getTransacao() {
		return transacao;
	}


	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
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


	public Socio getSocioPagador() {
		return socioPagador;
	}


	public void setSocioPagador(Socio socioPagador) {
		this.socioPagador = socioPagador;
	}


	public Login getOperador() throws StuDAOException{
		LoginDAO objOperadorDAO = new LoginDAO();
		Login objOperador = new Login();
		
		objOperador.setCpf(this.cpfOperador);
		objOperador = objOperadorDAO.consultarUsuNome(objOperador);
		
		return objOperador;
	}
	
}
