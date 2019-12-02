package beans;

import java.util.Date;

public class HistoricoSocio implements java.io.Serializable{

	private static final long serialVersionUID = 4010099103L;
	
	private Socio socio;
	private Date dataHistorico;
	private Date dataLancamento;
	private long cpfOperador;
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public Date getDataHistorico() {
		return dataHistorico;
	}
	public void setDataHistorico(Date dataHistorico) {
		this.dataHistorico = dataHistorico;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public long getCpfOperador() {
		return cpfOperador;
	}
	public void setCpfOperador(long cpfOperador) {
		this.cpfOperador = cpfOperador;
	}
	
	
}
